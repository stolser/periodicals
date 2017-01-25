package com.stolser.javatraining.webproject.controller.form.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MSG_SUCCESS;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.STATUS_CODE_SUCCESS;

public abstract class AbstractValidator implements Validator {
    private static ValidationResult successResult = new ValidationResult(STATUS_CODE_SUCCESS, MSG_SUCCESS);

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        Optional<ValidationResult> failedResult = checkParameter(paramValue, request);

        if (failedResult.isPresent()) {
            return failedResult.get();
        }

        return successResult;
    }

    /**
     * Returns an empty object if the parameter value is valid and an object describing the failure otherwise.
     */
    protected abstract Optional<ValidationResult> checkParameter(String paramValue, HttpServletRequest request);
}
