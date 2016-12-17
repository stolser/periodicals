package com.stolser.javatraining.webproject.controller.validator.user;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class RequestUserIdValidator implements Validator {
    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;
        long userIdFromUri = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        long userIdFromSession = HttpUtils.getUserIdFromSession(request);

        if (userIdFromUri == userIdFromSession) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MSG_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MSG_INCORRECT_USER_ID;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
