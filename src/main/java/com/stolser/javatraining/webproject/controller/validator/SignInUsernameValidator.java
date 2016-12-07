package com.stolser.javatraining.webproject.controller.validator;

import com.stolser.javatraining.webproject.model.entity.user.Login;
import com.stolser.javatraining.webproject.model.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

public class SignInUsernameValidator implements Validator {

    public SignInUsernameValidator() {
    }

    @Override
    public ValidationResult validate(String usernameValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        Login login = UserService.getInstance().findOneLoginByUserName(usernameValue);

        if (login != null) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MESSAGE_KEY_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MESSAGE_KEY_NO_SUCH_USER_NAME;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
