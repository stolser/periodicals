package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.request_processor.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.front_message.FrontMessageFactory;
import com.stolser.javatraining.webproject.controller.validator.front_message.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.ValidatorFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;
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

/**
 * Processes a POST request to persist one periodical. It handles both {@code create} and
 * {@code update} operations by analysing {@code periodicalOperationType} request parameter.
 */
public class PersistOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOnePeriodical.class);
    private static final String EXCEPTION_DURING_PERSISTING_PERIODICAL_WITH_ID = "Exception during persisting a periodical with id = {}.";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private FrontMessageFactory messageFactory = FrontMessageFactory.getInstance();

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        List<FrontendMessage> generalMessages = new ArrayList<>();

        Periodical periodicalToSave;
        try {
            periodicalToSave = HttpUtils.getPeriodicalFromRequest(request);
        } catch (Exception e) {
            processExceptionAndRedirect(request, response, e);
            return null;
        }

        Periodical.OperationType periodicalOperationType = getOperationTypeFromRequest(request);
        String redirectUri = getRedirectUriByOperationType(periodicalOperationType, periodicalToSave);

        request.getSession().setAttribute(PERIODICAL_ATTR_NAME, periodicalToSave);

        if (periodicalToSaveIsValid(periodicalToSave, request)) {
            generalMessages.add(messageFactory.getInfo(MSG_VALIDATION_PASSED_SUCCESS));
        } else {
            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        }

        Periodical periodicalInDb = periodicalService.findOneById(periodicalToSave.getId());

        Periodical.Status oldStatus = (periodicalInDb != null) ? periodicalInDb.getStatus() : null;
        Periodical.Status newStatus = periodicalToSave.getStatus();

        if (statusFromActiveToInactive(oldStatus, newStatus)) {
            if (periodicalService.hasActiveSubscriptions(periodicalToSave.getId())) {
                generalMessages.add(messageFactory.getWarning(MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_WARNING));
            }
        }

        Periodical persistedPeriodical;
        if (statusFromActiveOrInactiveToDiscarded(oldStatus, newStatus)) {

            if (tryToDiscardPeriodical(periodicalToSave)) {
                persistedPeriodical = periodicalService.findOneById(periodicalToSave.getId());

            } else {
                generalMessages.add(messageFactory.getError(MSG_PERIODICAL_HAS_ACTIVE_SUBSCRIPTIONS_ERROR));
                HttpUtils.addGeneralMessagesToSession(request, generalMessages);
                HttpUtils.sendRedirect(request, response, redirectUri);

                return null;
            }

        } else {
            persistedPeriodical = periodicalService.save(periodicalToSave);
        }

        if (persistedPeriodical != null) {
            addGeneralMessagesToSession(request, generalMessages, periodicalOperationType);
            return new DisplayAllPeriodicals().getViewName(request, response);

        } else {
            generalMessages.add(messageFactory.getError(MSG_PERIODICAL_PERSISTING_ERROR));
            HttpUtils.addGeneralMessagesToSession(request, generalMessages);
            HttpUtils.sendRedirect(request, response, redirectUri);

            return null;
        }
    }

    private Periodical.OperationType getOperationTypeFromRequest(HttpServletRequest request) {
        return Periodical.OperationType.valueOf(request
                .getParameter(PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME).toUpperCase());
    }

    private void processExceptionAndRedirect(HttpServletRequest request, HttpServletResponse response,
                                             Exception e) {
        List<FrontendMessage> generalMessages = new ArrayList<>();

        LOGGER.error(EXCEPTION_DURING_PERSISTING_PERIODICAL_WITH_ID,
                request.getParameter(ENTITY_ID_PARAM_NAME), e);

        generalMessages.add(messageFactory.getError(MSG_PERIODICAL_PERSISTING_ERROR));
        HttpUtils.addGeneralMessagesToSession(request, generalMessages);
        HttpUtils.sendRedirect(request, response, PERIODICAL_LIST_URI);
    }

    private void addGeneralMessagesToSession(HttpServletRequest request, List<FrontendMessage> generalMessages, Periodical.OperationType periodicalOperationType) {
        switch (periodicalOperationType) {
            case CREATE:
                generalMessages.add(messageFactory.getSuccess(MSG_PERIODICAL_CREATED_SUCCESS));
                break;

            case UPDATE:
                generalMessages.add(messageFactory.getSuccess(MSG_PERIODICAL_UPDATED_SUCCESS));
                break;
        }

        HttpUtils.addGeneralMessagesToSession(request, generalMessages);
    }

    private boolean tryToDiscardPeriodical(Periodical periodicalToSave) {
        int result = periodicalService.updateAndSetDiscarded(periodicalToSave);
        return result >= 1;
    }

    private boolean statusFromActiveOrInactiveToDiscarded(Periodical.Status oldStatus,
                                                          Periodical.Status newStatus) {
        return (ACTIVE.equals(oldStatus) || INACTIVE.equals(oldStatus))
                && DISCARDED.equals(newStatus);
    }

    private boolean statusFromActiveToInactive(Periodical.Status oldStatus, Periodical.Status newStatus) {
        return ACTIVE.equals(oldStatus) && INACTIVE.equals(newStatus);
    }

    private String getRedirectUriByOperationType(Periodical.OperationType periodicalOperationType,
                                                 Periodical periodicalToSave) {
        String redirectUri;
        switch (periodicalOperationType) {
            case CREATE:
                redirectUri = PERIODICAL_CREATE_NEW_URI;
                break;

            case UPDATE:
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
            messages.put(PERIODICAL_NAME_PARAM_NAME, messageFactory.getError(result.getMessageKey()));
        }

        result = factory.newValidator(PERIODICAL_NAME_PARAM_NAME)
                .validate(periodicalToSave.getCategory().toString(), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_NAME_PARAM_NAME, messageFactory.getError(result.getMessageKey()));
        }

        result = factory.newValidator(PERIODICAL_PUBLISHER_PARAM_NAME)
                .validate(periodicalToSave.getPublisher(), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_PUBLISHER_PARAM_NAME, messageFactory.getError(result.getMessageKey()));
        }

        result = factory.newValidator(PERIODICAL_COST_PARAM_NAME)
                .validate(String.valueOf(periodicalToSave.getOneMonthCost()), request);

        if (result.getStatusCode() != STATUS_CODE_SUCCESS) {
            isValid = false;
            messages.put(PERIODICAL_COST_PARAM_NAME, messageFactory.getError(result.getMessageKey()));
        }

        if (messages.size() > 0) {
            request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);
        }

        return isValid;
    }

}
