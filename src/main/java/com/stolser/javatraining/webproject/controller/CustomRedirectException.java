package com.stolser.javatraining.webproject.controller;

/**
 * Is thrown if an exception occurs during http redirection.
 */
public class CustomRedirectException extends RuntimeException {
    public CustomRedirectException(String message, Throwable cause) {
        super(message, cause);
    }
}
