package com.hes.test.exception;

public class SuchEntityAlreadyExistException extends RuntimeException {

    public SuchEntityAlreadyExistException() {
        super();
    }

    public SuchEntityAlreadyExistException(String message) {
        super(message);
    }

    public SuchEntityAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
