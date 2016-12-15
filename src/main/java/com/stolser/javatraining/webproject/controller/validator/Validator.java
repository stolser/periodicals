package com.stolser.javatraining.webproject.controller.validator;

import javax.servlet.http.HttpServletRequest;

public interface Validator {
    ValidationResult validate(String paramValue, HttpServletRequest request);
}
