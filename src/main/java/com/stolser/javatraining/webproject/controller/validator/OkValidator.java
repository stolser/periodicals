package com.stolser.javatraining.webproject.controller.validator;

public class OkValidator implements Validator {
    @Override
    public ValidationResult validate(String paramValue, Object... context) {
        return new ValidationResult(200, "validation.ok");
    }
}
