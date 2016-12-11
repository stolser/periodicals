package com.stolser.javatraining.webproject.controller.request_processor.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.CustomRedirectException;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersistOnePeriodical implements RequestProcessor {

    private static final String INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL = "Incorrect entityOperationType during persisting a periodical.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Periodical periodicalToSave;

        periodicalToSave = HttpUtils.getPeriodicalFromRequest(request);

        if (periodicalToSaveIsNotValid(periodicalToSave, request)) {
            request.getSession().setAttribute(ApplicationResources.PERIODICAL_ATTR_NAME, periodicalToSave);

            String entityOperationType = request.getParameter(ApplicationResources.ENTITY_OPERATION_TYPE_PARAM_NAME);
            String redirectUri = null;

            try {

                switch (entityOperationType) {
                    case "create":
                        redirectUri = ApplicationResources.PERIODICAL_CREATE_NEW_HREF;
                        response.sendRedirect(redirectUri);
                        return null;

                    case "update":
                        redirectUri = ApplicationResources.PERIODICAL_UPDATE_HREF + "/" +
                                periodicalToSave.getId();
                        response.sendRedirect(redirectUri);
                        return null;

                    default:
                        throw new IllegalArgumentException(INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL);
                }

            } catch (IOException e) {
                String message = HttpUtils.getRedirectionExceptionMessage(request,
                        redirectUri);

                throw new CustomRedirectException(message, e);
            }
        }

//        request.getSession().removeAttribute("periodical");
//        request.getSession().removeAttribute(ApplicationResources.MESSAGES_ATTR_NAME);
        PeriodicalService.getInstance().save(periodicalToSave);

        System.out.println("Persisted periodical: " + periodicalToSave);

        return new DisplayAllPeriodicals().getViewName(request, response);
    }

    private boolean periodicalToSaveIsNotValid(Periodical periodicalToSave, HttpServletRequest request) {
        boolean isNotValid = false;
        Map<String, FrontendMessage> messages = new HashMap<>();
        ValidatorFactory factory = ValidatorFactory.getInstance();

        ValidationResult result = factory.newValidator("periodicalName")
                .validate(periodicalToSave.getName(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalName", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCategory")
                .validate(periodicalToSave.getCategory(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalCategory", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalPublisher")
                .validate(periodicalToSave.getPublisher(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalPublisher", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCost")
                .validate(String.valueOf(periodicalToSave.getOneMonthCost()), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalCost", new FrontendMessage(result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        request.getSession().setAttribute(ApplicationResources.MESSAGES_ATTR_NAME, messages);

        return isNotValid;
    }

}
