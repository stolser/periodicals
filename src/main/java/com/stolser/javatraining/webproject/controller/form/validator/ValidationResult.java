package com.stolser.javatraining.webproject.controller.form.validator;

public class ValidationResult {
    /**
     * If equals to {@code STATUS_CODE_SUCCESS}, it means that validation has been passed successfully.
     * Otherwise - validation failed.
     */
    private int statusCode;
    /**
     * An i18n message key.
     */
    private String messageKey;

    public ValidationResult(int statusCode, String messageKey) {
        this.statusCode = statusCode;
        this.messageKey = messageKey;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
