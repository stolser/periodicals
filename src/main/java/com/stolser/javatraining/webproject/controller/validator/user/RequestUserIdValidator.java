package com.stolser.javatraining.webproject.controller.validator.user;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.validator.Validator;

import javax.servlet.http.HttpServletRequest;

public class RequestUserIdValidator implements Validator {
    @Override
    public ValidationResult validate(String paramValue, HttpServletRequest request) {
        int statusCode;
        String messageKey;
        long userIdFromUri = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        long userIdFromSession = HttpUtils.getUserIdFromSession(request);

        if (userIdFromUri == userIdFromSession) {
            statusCode = STATUS_CODE_SUCCESS;
            messageKey = MESSAGE_KEY_SUCCESS;
        } else {
            statusCode = STATUS_CODE_VALIDATION_FAILED;
            messageKey = MESSAGE_KYE_INCORRECT_USER_ID;
        }

        return new ValidationResult(statusCode, messageKey);
    }
}
