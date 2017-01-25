package com.stolser.javatraining.webproject.controller.form.validator;

public class ValidationProcessorException extends RuntimeException {
    public ValidationProcessorException(String message) {
        super(message);
    }

    public ValidationProcessorException(String message, Throwable cause) {
        super(message, cause);
    }
}
