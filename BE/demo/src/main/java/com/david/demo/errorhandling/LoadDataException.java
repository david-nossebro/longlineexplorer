package com.david.demo.errorhandling;

public class LoadDataException extends RuntimeException {

    public LoadDataException() {
        super();
    }

    public LoadDataException(String message) {
        super(message);
    }

    public LoadDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadDataException(Throwable cause) {
        super(cause);
    }

    protected LoadDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
