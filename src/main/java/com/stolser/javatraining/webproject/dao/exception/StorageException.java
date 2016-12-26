package com.stolser.javatraining.webproject.dao.exception;

public class StorageException extends RuntimeException {
    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
