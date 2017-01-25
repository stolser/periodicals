package com.stolser.javatraining.webproject.controller.form.validator.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a periodical publisher name contains only acceptable symbols.
 */
public class PeriodicalPublisherValidator extends AbstractValidator {
    private static ValidationResult failedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_PERIODICAL_PUBLISHER_ERROR);

    private PeriodicalPublisherValidator() {}

    private static class InstanceHolder {
        private static final PeriodicalPublisherValidator INSTANCE = new PeriodicalPublisherValidator();
    }

    public static PeriodicalPublisherValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String publisher, HttpServletRequest request) {
        if (isPublisherCorrect(publisher)) {
            return Optional.empty();
        }

        return Optional.of(failedResult);
    }

    private boolean isPublisherCorrect(String publisher) {
        return Pattern.matches(PERIODICAL_PUBLISHER_PATTERN_REGEX, publisher);
    }
}
