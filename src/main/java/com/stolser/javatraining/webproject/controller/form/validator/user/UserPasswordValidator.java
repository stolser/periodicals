package com.stolser.javatraining.webproject.controller.form.validator.user;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

public class UserPasswordValidator extends AbstractValidator {
    private static ValidationResult failedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_USER_PASSWORD_ERROR);

    private UserPasswordValidator() {}

    private static class InstanceHolder {
        private static final UserPasswordValidator INSTANCE = new UserPasswordValidator();
    }

    public static UserPasswordValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String password, HttpServletRequest request) {
        if (passwordMatchesRegex(password)) {
            return Optional.empty();
        }

        return Optional.of(failedResult);
    }

    private boolean passwordMatchesRegex(String password) {
        return Pattern.matches(USER_PASSWORD_PATTERN_REGEX, password);
    }
}
