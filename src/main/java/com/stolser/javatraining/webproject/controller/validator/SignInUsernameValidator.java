package com.stolser.javatraining.webproject.controller.validator;

import com.stolser.javatraining.webproject.model.entity.user.Login;
import com.stolser.javatraining.webproject.model.service.user.UserService;

public class SignInUsernameValidator implements Validator {

    public SignInUsernameValidator() {
    }

    @Override
    public ValidationResult validate(String usernameValue, Object... context) {
        int statusCode;
        String messageKey;

        Login login = UserService.getInstance().findOneLoginByUserName(usernameValue);

        if (login != null) {
            statusCode = 200;
            messageKey = "validation.ok";
        } else {
            statusCode = 404;
            messageKey = "validation.noSuchUserName";
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
