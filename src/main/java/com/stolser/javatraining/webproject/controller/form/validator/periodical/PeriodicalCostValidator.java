package com.stolser.javatraining.webproject.controller.form.validator.periodical;

import com.stolser.javatraining.webproject.controller.form.validator.AbstractValidator;
import com.stolser.javatraining.webproject.controller.form.validator.ValidationResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Checks whether a periodical cost is an integer number from the acceptable range.
 */
public class PeriodicalCostValidator extends AbstractValidator {
    private static ValidationResult failedResult =
            new ValidationResult(STATUS_CODE_VALIDATION_FAILED, MSG_PERIODICAL_COST_ERROR);

    private PeriodicalCostValidator() {}

    private static class InstanceHolder {
        private static final PeriodicalCostValidator INSTANCE = new PeriodicalCostValidator();
    }

    public static PeriodicalCostValidator getInstance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    protected Optional<ValidationResult> checkParameter(String periodicalCost, HttpServletRequest request) {
        if (isCostCorrect(periodicalCost)) {
            return Optional.empty();
        }

        return Optional.of(failedResult);
    }

    private boolean isCostCorrect(String periodicalCost) {
        return Pattern.matches(PERIODICAL_COST_PATTERN_REGEX, periodicalCost);
    }
}
