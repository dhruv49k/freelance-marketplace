package com.freelance.backend.exception;

public class UnauthorizedProposalAccessException extends RuntimeException {

    public UnauthorizedProposalAccessException(String message) {
        super(message);
    }
}