package com.stolser.javatraining.webproject.controller.form.validator.user;

import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UserPasswordValidator implements Validator {

    @Override
    public ValidationResult validate(String userEmail, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(USER_PASSWORD_PATTERN_REGEX, userEmail)) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_USER_PASSWORD_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
