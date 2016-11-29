package com.stolser.javatraining.webproject.controller.validator;

public class ValidationResult {
    private int statusCode;
    private String localeMessage;

    public ValidationResult(int statusCode, String localeMessage) {
        this.statusCode = statusCode;
        this.localeMessage = localeMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getLocaleMessage() {
        return localeMessage;
    }

    public void setLocaleMessage(String localeMessage) {
        this.localeMessage = localeMessage;
    }
}
