package com.stolser.javatraining.webproject.controller.validator.periodical;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class PeriodicalPublisherValidator implements Validator {

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(ApplicationResources.PERIODICAL_PUBLISHER_PATTERN_REGEX, paramValue)) {
            statusCode = ApplicationResources.STATUS_CODE_SUCCESS;
            messageKey = ApplicationResources.MSG_SUCCESS;
        } else {
            statusCode = ApplicationResources.STATUS_CODE_VALIDATION_FAILED;
            messageKey = ApplicationResources.MSG_PERIODICAL_PUBLISHER_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
