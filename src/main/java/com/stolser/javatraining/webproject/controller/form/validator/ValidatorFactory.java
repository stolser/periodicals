package com.stolser.javatraining.webproject.controller.form.validator;

import com.stolser.javatraining.webproject.controller.form.validator.periodical.PeriodicalCategoryValidator;
import com.stolser.javatraining.webproject.controller.form.validator.periodical.PeriodicalCostValidator;
import com.stolser.javatraining.webproject.controller.form.validator.periodical.PeriodicalNameValidator;
import com.stolser.javatraining.webproject.controller.form.validator.periodical.PeriodicalPublisherValidator;
import com.stolser.javatraining.webproject.controller.form.validator.user.RequestUserIdValidator;
import com.stolser.javatraining.webproject.controller.form.validator.user.UserEmailValidator;
import com.stolser.javatraining.webproject.controller.form.validator.user.UserPasswordValidator;

import static com.stolser.javatraining.webproject.controller.ApplicationResources.*;

/**
 * Produces validators for different parameter names.
 */
public class ValidatorFactory {
    private static final String THERE_IS_NO_VALIDATOR_FOR_SUCH_PARAM =
            "There is no validator for such a parameter!";

    public static Validator getPeriodicalNameValidator() {
        return PeriodicalNameValidator.getInstance();
    }

    public static Validator getPeriodicalCategoryValidator() {
        return PeriodicalCategoryValidator.getInstance();
    }

    public static Validator getPeriodicalPublisherValidator() {
        return PeriodicalPublisherValidator.getInstance();
    }

    public static Validator getPeriodicalCostValidator() {
        return PeriodicalCostValidator.getInstance();
    }

    public static Validator getRequestUserIdValidator() {
        return RequestUserIdValidator.getInstance();
    }

    public static Validator getUserPasswordValidator() {
        return UserPasswordValidator.getInstance();
    }

    /**
     * Returns a concrete validator for this specific parameter.
     *
     * @param paramName a http parameter name that need to be validated
     */
    public static Validator newValidator(String paramName) {
        switch (paramName) {
            case PERIODICAL_NAME_PARAM_NAME:
                return PeriodicalNameValidator.getInstance();

            case PERIODICAL_CATEGORY_PARAM_NAME:
                return PeriodicalCategoryValidator.getInstance();

            case PERIODICAL_PUBLISHER_PARAM_NAME:
                return PeriodicalPublisherValidator.getInstance();

            case PERIODICAL_COST_PARAM_NAME:
                return PeriodicalCostValidator.getInstance();

            case USER_EMAIL_PARAM_NAME:
                return UserEmailValidator.getInstance();

            case USER_PASSWORD_PARAM_NAME:
                return UserPasswordValidator.getInstance();

            default:
                throw new ValidationProcessorException(THERE_IS_NO_VALIDATOR_FOR_SUCH_PARAM);
        }
    }
}
