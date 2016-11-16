package com.stolser.javatraining.webproject.model;

public class CustomSqlException extends RuntimeException {
    public CustomSqlException() {
    }

    public CustomSqlException(String message) {
        super(message);
    }
}
