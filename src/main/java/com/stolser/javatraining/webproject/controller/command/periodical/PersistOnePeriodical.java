package com.stolser.javatraining.webproject.controller.command.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.command.RequestProcessor;
import com.stolser.javatraining.webproject.controller.utils.Utils;
import com.stolser.javatraining.webproject.controller.validator.FrontendMessage;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;
import com.stolser.javatraining.webproject.controller.validator.ValidatorFactory;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class PersistOnePeriodical implements RequestProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistOnePeriodical.class);

    @Override
    public String getViewName(HttpServletRequest request, HttpServletResponse response) {
        Periodical periodicalToSave;


        try {
            periodicalToSave = Utils.getPeriodicalFromRequest(request);

            if (periodicalToSaveIsNotValid(periodicalToSave, request)) {
                request.getSession().setAttribute("periodical", periodicalToSave);
                response.sendRedirect(ApplicationResources.PERIODICAL_CREATE_NEW_HREF);

                return null;
            }

            request.getSession().removeAttribute("periodical");
            request.getSession().removeAttribute(ApplicationResources.MESSAGES_ATTR_NAME);
            PeriodicalService.getInstance().save(periodicalToSave);

        } catch (Exception e) {
            String message = Utils.getExceptionMessageForRequestProcessor(request, e);
            LOGGER.debug(message, e);

            throw new RuntimeException(message, e);
        }

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
            messages.put("periodicalName", new FrontendMessage("periodicalName", result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCategory")
                .validate(periodicalToSave.getCategory(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalCategory", new FrontendMessage("periodicalCategory", result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalPublisher")
                .validate(periodicalToSave.getPublisher(), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalPublisher", new FrontendMessage("periodicalPublisher", result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        result = factory.newValidator("periodicalCost")
                .validate(String.valueOf(periodicalToSave.getOneMonthCost()), request);

        if (result.getStatusCode() != Validator.STATUS_CODE_SUCCESS) {
            isNotValid = true;
            messages.put("periodicalCost", new FrontendMessage("periodicalCost", result.getMessageKey(),
                    FrontendMessage.MessageType.ERROR));
        }

        request.getSession().setAttribute(ApplicationResources.MESSAGES_ATTR_NAME, messages);

        return isNotValid;
    }

}
