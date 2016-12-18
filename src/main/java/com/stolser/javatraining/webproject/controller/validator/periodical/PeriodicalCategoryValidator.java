package com.stolser.javatraining.webproject.controller.validator.periodical;

import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;
import com.stolser.javatraining.webproject.model.entity.periodical.PeriodicalCategory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class PeriodicalCategoryValidator implements Validator {

    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Arrays.stream(PeriodicalCategory.values())
                .map(Enum::name)
                .collect(Collectors.toList())
                .contains(paramValue)) {

            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_PERIODICAL_CATEGORY_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
