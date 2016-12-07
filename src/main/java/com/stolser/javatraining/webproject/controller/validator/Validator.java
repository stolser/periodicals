package com.stolser.javatraining.webproject.controller.validator;

import javax.servlet.http.HttpServletRequest;

public interface Validator {
    int STATUS_CODE_SUCCESS = 200;
    int STATUS_CODE_VALIDATION_FAILED = 412;
    String MESSAGE_KEY_SUCCESS = "validation.ok";
    String MESSAGE_KEY_NO_SUCH_USER_NAME = "validation.noSuchUserName";
    String MESSAGE_KEY_USER_IS_BLOCKED = "validation.userIsBlocked";
    String MESSAGE_KEY_PERIODICAL_NAME_ERROR = "periodicalName.validationError";
    String MESSAGE_KEY_PERIODICAL_NAME_DUPLICATION = "periodicalName.duplicationError";
    String MESSAGE_KEY_PERIODICAL_PUBLISHER_ERROR = "periodicalPublisher.validationError";
    String MESSAGE_KEY_PERIODICAL_CATEGORY_ERROR = "periodicalCategory.validationError";
    String MESSAGE_KEY_PERIODICAL_COST_ERROR = "periodicalCost.validationError";
    String PERIODICAL_NAME_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s!&?$#@-]{2,45}";
    String PERIODICAL_PUBLISHER_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s-]{2,45}";
    String PERIODICAL_CATEGORY_PATTERN_REGEX = "[а-яА-ЯіІїЇєЄёЁ\\w\\s-]{2,45}";
    String PERIODICAL_COST_PATTERN_REGEX = "(0|[1-9]{1}\\d{0,3})(\\.\\d{0,2})?";

    ValidationResult validate(String paramValue, HttpServletRequest request);
}
