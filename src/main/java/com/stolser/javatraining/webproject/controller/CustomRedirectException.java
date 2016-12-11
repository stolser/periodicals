package com.stolser.javatraining.webproject.controller;

public class CustomRedirectException extends RuntimeException {
    public CustomRedirectException(String message, Throwable cause) {
        super(message, cause);
    }
}
