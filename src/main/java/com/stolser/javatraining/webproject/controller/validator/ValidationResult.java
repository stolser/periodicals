package com.stolser.javatraining.webproject.controller.validator;

public class ValidationResult {
    private int statusCode;
    private String messageKey;

    public ValidationResult(int statusCode, String messageKey) {
        this.statusCode = statusCode;
        this.messageKey = messageKey;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
