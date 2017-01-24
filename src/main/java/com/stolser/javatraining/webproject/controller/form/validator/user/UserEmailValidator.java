package com.stolser.javatraining.webproject.controller.form.validator.user;

import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form.validator.Validator;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UserEmailValidator implements Validator {

    @Override
    public ValidationResult validate(String userEmail, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        if (Pattern.matches(USER_EMAIL_PATTERN_REGEX, userEmail)) {
            if (emailExistsInDb(userEmail)) {
                statusCode = STATUS_CODE_VALIDATION_FAILED;
                messageKey = MSG_USER_EMAIL_DUPLICATION_ERROR;
            } else {
                statusCode = STATUS_CODE_SUCCESS;
                messageKey = MSG_SUCCESS;
            }
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_USER_EMAIL_ERROR;
        }

        return new ValidationResult(statusCode, messageKey);
    }

    private boolean emailExistsInDb(String userEmail) {
        return UserServiceImpl.getInstance().emailExistsInDb(userEmail);
    }
}
