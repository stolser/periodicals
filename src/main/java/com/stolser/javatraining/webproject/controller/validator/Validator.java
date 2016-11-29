package com.stolser.javatraining.webproject.controller.validator;

public interface Validator {
    ValidationResult validate(String paramValue, Object... context);
}
