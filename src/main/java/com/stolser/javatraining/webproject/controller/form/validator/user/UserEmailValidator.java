package com.stolser.javatraining.webproject.controller.form.validator.user;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;
import com.stolser.javatraining.webproject.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UserEmailValidator extends AbstractValidator {
    private static ValidationResult regexFailedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_USER_EMAIL_REGEX_ERROR);
    private static ValidationResult duplicationFailedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_USER_EMAIL_DUPLICATION_ERROR);

    private UserEmailValidator() {}

    private static class InstanceHolder {
        private static final UserEmailValidator INSTANCE = new UserEmailValidator();
    }

    public static UserEmailValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String userEmail, HttpServletRequest request) {
        // todo: remove returns;
        if (emailMatchesRegex(userEmail)) {
            if (emailDoesNotExistInDb(userEmail)) {
                return Optional.empty();
            }

            return Optional.of(duplicationFailedResult);
        }

        return Optional.of(regexFailedResult);
    }

    private boolean emailMatchesRegex(String userEmail) {
        return Pattern.matches(USER_EMAIL_PATTERN_REGEX, userEmail);
    }

    private boolean emailDoesNotExistInDb(String userEmail) {
        return !UserServiceImpl.getInstance().emailExistsInDb(userEmail);
    }
}
