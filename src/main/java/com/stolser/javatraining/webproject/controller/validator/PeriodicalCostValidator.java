package com.stolser.javatraining.webproject.controller.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class PeriodicalCostValidator implements Validator{
    public PeriodicalCostValidator() {
    }

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(PERIODICAL_COST_PATTERN_REGEX, paramValue)) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MESSAGE_KEY_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MESSAGE_KEY_PERIODICAL_COST_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
