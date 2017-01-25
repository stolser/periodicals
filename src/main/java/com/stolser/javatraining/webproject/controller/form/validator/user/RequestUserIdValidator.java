package com.stolser.javatraining.webproject.controller.form.validator.user;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.controller.utils.HttpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.MSG_INCORRECT_USER_ID;
import static com.stolser.javatraining.webproject.controller.ApplicationResources.STATUS_CODE_VALIDATION_FAILED;

/**
 * Checks whether a userId in the request uri is the same as the id of a current user in the session.
 */
public class RequestUserIdValidator extends AbstractValidator {
    private static ValidationResult failedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_INCORRECT_USER_ID);

    private RequestUserIdValidator() {}

    private static class InstanceHolder {
        private static final RequestUserIdValidator INSTANCE = new RequestUserIdValidator();
    }

    public static RequestUserIdValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String paramName, HttpServletRequest request) {
        if (userAccessesHisAccount(request)) {
            return Optional.empty();
        }

        return Optional.of(failedResult);
    }

    private boolean userAccessesHisAccount(HttpServletRequest request) {
        long userIdFromUri = HttpUtils.getFirstIdFromUri(request.getRequestURI());
        long userIdFromSession = HttpUtils.getUserIdFromSession(request);

        return userIdFromUri == userIdFromSession;
    }
}
