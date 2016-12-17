package com.stolser.javatraining.webproject.controller.validator.periodical;

import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class PeriodicalNameValidator implements Validator {
    private static final String INCORRECT_ENTITY_OPERATION_TYPE_DURING_VALIDATION = "Incorrect entityOperationType during validation!";

    @Override
    public ValidationResult validate(String periodicalName, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        String entityOperationType = checkOperationType(request);
        long periodicalId = Long.valueOf(request.getParameter(ENTITY_ID_PARAM_NAME));

        if (nameHasIncorrectSymbols(periodicalName)) {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_NAME_ERROR;

        } else if (nameIsNotUnique(entityOperationType, periodicalId, periodicalName)) {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_NAME_DUPLICATION;

        } else {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        }

        return new ValidationResult(statusCode, messageKey);
    }

    private boolean nameIsNotUnique(String entityOperationType, long periodicalId, String periodicalName) {
        Periodical periodicalWithSuchNameInDb = PeriodicalServiceImpl.getInstance().findOneByName(periodicalName);
        /*
         * if this is 'create' --> there must not be any periodical with the same name in the db;
         * if this is 'update' --> we exclude this periodical from validation;
         */
        return (periodicalWithSuchNameInDb != null) &&
                (("create".equals(entityOperationType) ||
                        ("update".equals(entityOperationType)
                                && (periodicalId != periodicalWithSuchNameInDb.getId()))));
    }

    private boolean nameHasIncorrectSymbols(String periodicalName) {
        return !Pattern.matches(PERIODICAL_NAME_PATTERN_REGEX, periodicalName);
    }

    private String checkOperationType(HttpServletRequest request) {
        String entityOperationType = request.getParameter(ENTITY_OPERATION_TYPE_PARAM_ATTR_NAME);  // must not be null...
        System.out.println("entityOperationType = " + entityOperationType);

        if (!"create".equals(entityOperationType)
                && !"update".equals(entityOperationType)) {
            // ... and must be 'create' or 'update'. Otherwise something wrong with this request;
            throw new IllegalArgumentException(INCORRECT_ENTITY_OPERATION_TYPE_DURING_VALIDATION);
        }

        return entityOperationType;
    }
}
