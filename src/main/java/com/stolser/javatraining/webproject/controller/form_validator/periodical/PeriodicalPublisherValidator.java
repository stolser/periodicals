package com.stolser.javatraining.webproject.controller.form_validator.periodical;

import com.stolser.javatraining.webproject.controller.form_validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form_validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a periodical publisher name contains only acceptable symbols.
 */
public class PeriodicalPublisherValidator implements Validator {

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(PERIODICAL_PUBLISHER_PATTERN_REGEX, paramValue)) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_PUBLISHER_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
