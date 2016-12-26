package com.stolser.javatraining.webproject.controller.form_validator.user;

import com.stolser.javatraining.webproject.controller.utils.HttpUtils;
import com.stolser.javatraining.webproject.controller.form_validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.form_validator.Validator;

import javax.servlet.http.HttpServletRequest;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a userId in the request uri is the same as the id of a current user in the session.
 */
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
