package com.stolser.javatraining.webproject.controller.form.validator.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form.validator.Validator;
import com.stolser.javatraining.webproject.controller.form.validator.exception.ValidationProcessorException;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a periodical name is acceptable for depending the operation ('create' or 'update').
 */
public class PeriodicalNameValidator implements Validator {
    private static final String INCORRECT_ENTITY_OPERATION_TYPE_DURING_VALIDATION =
            "Incorrect periodicalOperationType during validation!";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();

    @Override
    public ValidationResult validate(String periodicalName, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        Periodical.OperationType operationType = getOperationType(request);
        long periodicalId = Long.valueOf(request.getParameter(ENTITY_ID_PARAM_NAME));

        if (hasNameIncorrectSymbols(periodicalName)) {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_NAME_ERROR;

        } else if (isNameNotUnique(operationType, periodicalId, periodicalName)) {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_NAME_DUPLICATION;

        } else {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        }

        return new ValidationResult(statusCode, messageKey);
    }

    private boolean isNameNotUnique(Periodical.OperationType periodicalOperationType,
                                    long periodicalId, String periodicalName) {
        Periodical periodicalWithSuchNameInDb = periodicalService.findOneByName(periodicalName);
        /*
         * if this is 'create' --> there must not be any periodical with the same name in the db;
         * if this is 'update' --> we exclude this periodical from validation;
         */
        return periodicalNameExists(periodicalWithSuchNameInDb)
                && (isOperationCreate(periodicalOperationType)
                || (isOperationUpdate(periodicalOperationType) &&
                (periodicalId != periodicalWithSuchNameInDb.getId())));
    }

    private boolean isOperationUpdate(Periodical.OperationType periodicalOperationType) {
        return Periodical.OperationType.UPDATE.equals(periodicalOperationType);
    }

    private boolean periodicalNameExists(Periodical periodicalWithSuchNameInDb) {
        return periodicalWithSuchNameInDb != null;
    }

    private boolean isOperationCreate(Periodical.OperationType periodicalOperationType) {
        return Periodical.OperationType.CREATE.equals(periodicalOperationType);
    }

    private boolean hasNameIncorrectSymbols(String periodicalName) {
        return !Pattern.matches(PERIODICAL_NAME_PATTERN_REGEX, periodicalName);
    }

    private Periodical.OperationType getOperationType(HttpServletRequest request) {
        try {
            return Periodical.OperationType.valueOf(request
                    .getParameter(PERIODICAL_OPERATION_TYPE_PARAM_ATTR_NAME).toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new ValidationProcessorException(INCORRECT_ENTITY_OPERATION_TYPE_DURING_VALIDATION, e);
        }
    }
}
