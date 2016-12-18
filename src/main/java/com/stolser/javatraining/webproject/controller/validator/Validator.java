package com.stolser.javatraining.webproject.controller.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Specifies an interface for classes that contain validation logic for different http parameters.
 */
public interface Validator {
    ValidationResult validate(String paramValue, HttpServletRequest request);
}
