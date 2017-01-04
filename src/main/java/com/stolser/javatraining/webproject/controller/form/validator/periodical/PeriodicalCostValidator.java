package com.stolser.javatraining.webproject.controller.form.validator.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a periodical cost is an integer number from the acceptable range.
 */
public class PeriodicalCostValidator implements Validator {

    @Override
    public ValidationResult validate(String periodicalCost, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(PERIODICAL_COST_PATTERN_REGEX, periodicalCost)) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_COST_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
