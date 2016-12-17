package com.stolser.javatraining.webproject.controller.validator.periodical;

import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class PeriodicalCostValidator implements Validator {

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(PERIODICAL_COST_PATTERN_REGEX, paramValue)) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_COST_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
