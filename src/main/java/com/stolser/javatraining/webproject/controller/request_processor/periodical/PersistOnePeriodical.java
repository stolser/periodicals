package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.ValidatorFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static com.stolser.javatraining.webproject.model.entity.periodical.Periodical.Status.*;

public class PersistOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOnePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();

        Periodical periodicalToSave;
        try {
            periodicalToSave = HttpUtils.getPeriodicalFromRequest(request);
        } catch (Exception e) {
            LOGGER.debug("Exception during persisting a periodical with id = {}.",
                    request.getParameter(ENTITY_ID_PARAM_NAME), e);
            generalMessages.add(new FrontendMessage(MSG_PERIODICAL_PERSISTING_ERROR,
                    FrontendMessage.MessageType.ERROR));
            HttpUtils.addGeneralMessagesToSession(request, generalMessages);
            HttpUtils.sendRedirect(request, response, PERIODICAL_LIST_URI);
            return null;
        }

        String entityOperationType = request.getParameter(ENTITY_OPERATION_TYPE_PARAM_ATTR_NAME);
        String redirectUri = getRedirectUriByOperationType(entityOperationType, periodicalToSave);

        request.getSession().setAttribute(PERIODICAL_ATTR_NAME, periodicalToSave);

        if (periodicalToSaveIsValid(periodicalToSave, request)) {
            generalMessages.add(new FrontendMessage(MSG_VALIDATION_PASSED_SUCCESS,
                    FrontendMessage.MessageType.INFO));

        } else {
            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        }

        PeriodicalService periodicalService = PeriodicalService.getInstance();
        Periodical periodicalInDb = periodicalService.findOneById(periodicalToSave.getId());

        Periodical.Status oldStatus = null;
        if (periodicalInDb != null) {
            oldStatus = periodicalInDb.getStatus();
        }

        Periodical.Status newStatus = periodicalToSave.getStatus();

        if (VISIBLE.equals(oldStatus) && INVISIBLE.equals(newStatus)) {
            if (periodicalService.hasActiveSubscriptions(periodicalToSave.getId())) {
                generalMessages.add(new FrontendMessage(MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_WARNING,
                        FrontendMessage.MessageType.WARNING));
            }
        }

        if ((VISIBLE.equals(oldStatus) || INVISIBLE.equals(oldStatus))
                && DISCARDED.equals(newStatus)
                && periodicalService.hasActiveSubscriptions(periodicalToSave.getId())) {

            generalMessages.add(new FrontendMessage(MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_ERROR,
                    FrontendMessage.MessageType.ERROR));

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);
            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        }

        Periodical persistedPeriodical;
        if ((VISIBLE.equals(oldStatus) || INVISIBLE.equals(oldStatus))
                && DISCARDED.equals(newStatus)) {

//            persistedPeriodical = periodicalService.save(periodicalToSave);

            if (periodicalService.discard(periodicalToSave)) {
                persistedPeriodical = periodicalService.findOneById(periodicalToSave.getId());

            } else {
                generalMessages.add(new FrontendMessage(MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_ERROR,
                        FrontendMessage.MessageType.ERROR));

                HttpUtils.addGeneralMessagesToSession(request, generalMessages);
                HttpUtils.sendRedirect(request, response, redirectUri);
                return null;
            }

            // update status by periodicalService.discard(periodicalToDiscard) in a transaction
            // with conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            // If inside Dao periodical hasActiveSubscriptions --> new CustomSqlException();
        } else {
            persistedPeriodical = periodicalService.save(periodicalToSave);
        }

        if (persistedPeriodical != null) {
            switch (entityOperationType) {
                case "create":
                    generalMessages.add(new FrontendMessage(MSG_PERIODICAL_CREATED_SUCCESS,
                            FrontendMessage.MessageType.SUCCESS));
                    break;

                case "update":
                    generalMessages.add(new FrontendMessage(MSG_PERIODICAL_UPDATED_SUCCESS,
                            FrontendMessage.MessageType.SUCCESS));
                    break;
            }

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            System.out.println("Persisted periodical: " + periodicalToSave);

            return new DisplayAllPeriodicals().getViewName(request, response);

        } else {
            generalMessages.add(new FrontendMessage(MSG_PERIODICAL_PERSISTING_ERROR,
                    FrontendMessage.MessageType.ERROR));
            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        }
    }

    private String getRedirectUriByOperationType(String entityOperationType, Periodical periodicalToSave) {
        String redirectUri;
        switch (entityOperationType) {
            case "create":
                redirectUri = PERIODICAL_CREATE_NEW_URI;
                break;

            case "update":
                redirectUri = PERIODICAL_LIST_URI + "/" + periodicalToSave.getId() + "/update";
                break;

            default:
                throw new IllegalArgumentException(INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL);
        }
        return redirectUri;
    }

    private boolean periodicalToSaveIsValid(Periodical periodicalToSave, HttpServletRequest request) {
        boolean isValid = true;
        Map<String, FrontendMessage> messages = new HashMap<>();
        ValidatorFactory factory = ValidatorFactory.getInstance();

        ValidationResult result = factory.newValidator(PERIODICAL_NAME_PARAM_NAME)
                .validate(periodicalToSave.getName(), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_NAME_PARAM_NAME, new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator(PERIODICAL_NAME_PARAM_NAME)
                .validate(periodicalToSave.getCategory().toString(), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_NAME_PARAM_NAME, new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator(PERIODICAL_PUBLISHER_PARAM_NAME)
                .validate(periodicalToSave.getPublisher(), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_PUBLISHER_PARAM_NAME, new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator(PERIODICAL_COST_PARAM_NAME)
                .validate(String.valueOf(periodicalToSave.getOneMonthCost()), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_COST_PARAM_NAME, new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        if (messages.size() > 0) {
            request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        }

        return isValid;
    }

}
