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

public class PersistOnePeriodical implements RequestProcessor {

    private static final String INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL = "Incorrect entityOperationType during persisting a periodical.";

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Periodical periodicalToSave = HttpUtils.getPeriodicalFromRequest(request);
        String entityOperationType = request.getParameter(ENTITY_OPERATION_TYPE_PARAM_NAME);
        String redirectUri = getRedirectUriByOperationType(entityOperationType, periodicalToSave);
        List<FrontendMessage> generalMessages = new ArrayList<>();

        if (periodicalToSaveIsNotValid(periodicalToSave, request)) {
            request.getSession().setAttribute(PERIODICAL_ATTR_NAME, periodicalToSave);

            HttpUtils.sendRedirect(request, response, redirectUri);
            return null;
        } else {
            generalMessages.add(new FrontendMessage("validation.passedSuccessfully.success",
                    FrontendMessage.MessageType.INFO));
        }

//        request.getSession().removeAttribute("periodical");
//        request.getSession().removeAttribute(ApplicationResources.MESSAGES_ATTR_NAME);
        PeriodicalService periodicalService = PeriodicalService.getInstance();
        Periodical persistedPeriodical = periodicalService.save(periodicalToSave);

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

            request.getSession().setAttribute(PERIODICAL_ATTR_NAME, periodicalToSave);
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
                redirectUri = PERIODICAL_UPDATE_HREF + "/" +
                        periodicalToSave.getId();
                break;

            default:
                throw new IllegalArgumentException(INCORRECT_OPERATION_DURING_PERSISTING_A_PERIODICAL);
        }
        return redirectUri;
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

        request.getSession().setAttribute(MESSAGES_ATTR_NAME, messages);

        return isNotValid;
    }

}
