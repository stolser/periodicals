package com.stolser.javatraining.webproject.controller.form.validator.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationProcessorException;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.model.entity.periodical.Periodical;
import com.stolser.javatraining.webproject.service.PeriodicalService;
import com.stolser.javatraining.webproject.service.impl.PeriodicalServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;
import static java.util.Objects.nonNull;

/**
 * Checks whether a periodical name is acceptable for depending the operation ('create' or 'update').
 */
public class PeriodicalNameValidator extends AbstractValidator {
    private static final String INCORRECT_ENTITY_OPERATION_TYPE_DURING_VALIDATION =
            "Incorrect periodicalOperationType during validation!";
    private PeriodicalService periodicalService = PeriodicalServiceImpl.getInstance();
    private static ValidationResult incorrectFailedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_PERIODICAL_NAME_INCORRECT);
    private static ValidationResult duplicationFailedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_PERIODICAL_NAME_DUPLICATION);

    private PeriodicalNameValidator() {}

    private static class InstanceHolder {
        private static final PeriodicalNameValidator INSTANCE = new PeriodicalNameValidator();
    }

    public static PeriodicalNameValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String periodicalName, HttpServletRequest request) {
        if (nameDoesNotMatchRegex(periodicalName)) {
            return Optional.of(incorrectFailedResult);
        }

        if (isNameNotUnique(request, periodicalName)) {
            return Optional.of(duplicationFailedResult);
        }

        return Optional.empty();
    }

    private boolean nameDoesNotMatchRegex(String periodicalName) {
        return !Pattern.matches(PERIODICAL_NAME_PATTERN_REGEX, periodicalName);
    }

    private boolean isNameNotUnique(HttpServletRequest request, String periodicalName) {
        Periodical.OperationType operationType = getOperationType(request);
        long periodicalId = Long.parseLong(request.getParameter(ENTITY_ID_PARAM_NAME));
        Periodical periodicalWithSuchNameInDb = periodicalService.findOneByName(periodicalName);
        /*
         * if this is 'create' --> there must not be any periodical with the same name in the db;
         * if this is 'update' --> we exclude this periodical from validation;
         * Sorry for comments!
         */
        return nonNull(periodicalWithSuchNameInDb)
                && (isOperationCreate(operationType)
                || (isOperationUpdate(operationType) &&
                (periodicalId != periodicalWithSuchNameInDb.getId())));
    }

    private boolean isOperationUpdate(Periodical.OperationType periodicalOperationType) {
        return Periodical.OperationType.UPDATE.equals(periodicalOperationType);
    }

    private boolean isOperationCreate(Periodical.OperationType periodicalOperationType) {
        return Periodical.OperationType.CREATE.equals(periodicalOperationType);
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
