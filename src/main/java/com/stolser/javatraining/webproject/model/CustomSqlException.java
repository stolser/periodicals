package com.stolser.javatraining.webproject.model;

public class CustomSqlException extends RuntimeException {
    public CustomSqlException() {
    }

    public CustomSqlException(String message) {
        super(message);
    }

    public CustomSqlException(Throwable cause) {
        super(cause);
    }

    public CustomSqlException(String message, Throwable cause) {
        super(message, cause);
    }
}
