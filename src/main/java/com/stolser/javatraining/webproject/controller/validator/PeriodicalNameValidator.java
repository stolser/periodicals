package com.stolser.javatraining.webproject.controller.validator;

import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.model.service.periodical.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class PeriodicalNameValidator implements Validator {

    public PeriodicalNameValidator() {
    }

    @Override
    public ValidationResult validate(String periodicalName, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        String entityOperationType = checkOperationType(request);
        long periodicalId = Long.valueOf(request.getParameter("entityId"));
        System.out.println("entityId = " + periodicalId);

        Periodical periodicalWithSuchNameInDb = PeriodicalService.getInstance().findOneByName(periodicalName);

        if (!Pattern.matches(PERIODICAL_NAME_PATTERN_REGEX, periodicalName)) {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MESSAGE_KEY_PERIODICAL_NAME_ERROR;

        } else if ((("create".equals(entityOperationType)
                || ("update".equals(entityOperationType) && (periodicalId != periodicalWithSuchNameInDb.getId()))))
                && (periodicalWithSuchNameInDb != null)) {
            /* if this is 'create' --> there must not be any periodical with the same name in the db;
            if this is 'update' --> we exclude this periodical from validation;
            * */
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MESSAGE_KEY_PERIODICAL_NAME_DUPLICATION;
        } else {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MESSAGE_KEY_SUCCESS;
        }

        return new ValidationResult(statusCode, messageKey);
    }

    private String checkOperationType(HttpServletRequest request) {
        String entityOperationType = request.getParameter("entityOperationType");  // must not be null...
        System.out.println("entityOperationType = " + entityOperationType);

        if (!"create".equals(entityOperationType)
                && !"update".equals(entityOperationType)) {
            // ... and must be 'create' or 'update'. Otherwise something wrong with this request;
            throw new IllegalArgumentException("Incorrect request for validation!");
        }

        return entityOperationType;
    }
}
