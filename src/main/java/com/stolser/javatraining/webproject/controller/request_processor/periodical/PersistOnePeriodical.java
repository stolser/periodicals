package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;
import com.stolser.javatraining.webproject.controller.validator.ValidatorFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static com.stolser.javatraining.webproject.model.entity.periodical.Periodical.Status.*;

public class PersistOnePeriodical implements RequestProcessor {

    private static final String INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL = "Incorrect entityOperationType during persisting a periodical.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();
        Periodical periodicalToSave = HttpUtils.getPeriodicalFromRequest(request);
        String entityOperationType = request.getParameter(ENTITY_OPERATION_TYPE_PARAM_NAME);
        String redirectUri = getRedirectUriByOperationType(entityOperationType, periodicalToSave);

        request.getSession().setAttribute(PERIODICAL_ATTR_NAME, periodicalToSave);

        if (periodicalToSaveIsValid(periodicalToSave, request)) {
            generalMessages.add(new FrontendMessage("validation.passedSuccessfully.success",
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
                generalMessages.add(new FrontendMessage("validation.periodicalHasActiveSubscriptions.warning",
                        FrontendMessage.MessageType.WARNING));
            }
        }

        if ((VISIBLE.equals(oldStatus) || INVISIBLE.equals(oldStatus))
                && DISCARDED.equals(newStatus)
                && periodicalService.hasActiveSubscriptions(periodicalToSave.getId())) {

            generalMessages.add(new FrontendMessage("validation.periodicalHasActiveSubscriptions.error",
                    FrontendMessage.MessageType.ERROR));

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);
            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        }

        Periodical persistedPeriodical;
        if ((VISIBLE.equals(oldStatus) || INVISIBLE.equals(oldStatus))
                && DISCARDED.equals(newStatus)) {

            persistedPeriodical = periodicalService.save(periodicalToSave);
            // update status by periodicalService.discardThis(periodicalToSave) in a transaction
            // with conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            // If inside Dao periodical hasActiveSubscriptions --> new CustomSqlException();
        } else {
            persistedPeriodical = periodicalService.save(periodicalToSave);
        }

        if (persistedPeriodical != null) {
            switch (entityOperationType) {
                case "create":
                    generalMessages.add(new FrontendMessage("periodicalCreatedNew.success",
                            FrontendMessage.MessageType.SUCCESS));
                    break;

                case "update":
                    generalMessages.add(new FrontendMessage("periodicalUpdated.success",
                            FrontendMessage.MessageType.SUCCESS));
                    break;
            }

            HttpUtils.addGeneralMessagesToSession(request, generalMessages);

            System.out.println("Persisted periodical: " + periodicalToSave);

            return new DisplayAllPeriodicals().getViewName(request, response);

        } else {
            generalMessages.add(new FrontendMessage("periodicalPersisting.error",
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
                redirectUri = PERIODICAL_CREATE_NEW_HREF;
                break;

            case "update":
                redirectUri = PERIODICAL_LIST_HREF + "/" + periodicalToSave.getId() + "/update";
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

        ValidationResult result = factory.newValidator("periodicalName")
                .validate(periodicalToSave.getName(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put("periodicalName", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCategory")
                .validate(periodicalToSave.getCategory().toString(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put("periodicalCategory", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalPublisher")
                .validate(periodicalToSave.getPublisher(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put("periodicalPublisher", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCost")
                .validate(String.valueOf(periodicalToSave.getOneMonthCost()), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put("periodicalCost", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        if (messages.size() > 0) {
            request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        }

        return isValid;
    }

}
