package com.freelance.backend.exception;

public class UnauthorizedProjectAccessException extends RuntimeException {

    public UnauthorizedProjectAccessException(String message) {
        super(message);
    }
}