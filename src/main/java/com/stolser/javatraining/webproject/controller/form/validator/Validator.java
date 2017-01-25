package com.stolser.javatraining.webproject.controller.form.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Specifies an interface for classes that contain validation logic for different http parameters.
 */
public interface Validator {
    /**
     * Checks whether passed parameter value is correct and valid.
     * @param paramValue to be validated
     * @param request http request. Can contain extra relative data necessary for validation
     * @return the result of validation
     */
    ValidationResult validate(String paramValue, HttpServletRequest request);
}
