package com.stolser.javatraining.webproject.controller.validator.user;

import com.stolser.javatraining.webproject.controller.ApplicationResources;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;
import com.stolser.javatraining.webproject.model.entity.user.Credential;
import com.stolser.javatraining.webproject.model.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class SignInUsernameValidator implements Validator {

    @Override
    public ValidationResult validate(String usernameValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;

        Credential credential = UserServiceImpl.getInstance().findOneCredentialByUserName(usernameValue);

        if (credential != null) {
            statusCode = ApplicationResources.STATUS_CODE_SUCCESS;
            messageKey = ApplicationResources.MSG_SUCCESS;
        } else {
            statusCode = ApplicationResources.STATUS_CODE_VALIDATION_FAILED;
            messageKey = ApplicationResources.MSG_NO_SUCH_USER_NAME;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
