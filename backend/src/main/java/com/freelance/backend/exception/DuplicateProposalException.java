package com.freelance.backend.exception;

public class DuplicateProposalException extends RuntimeException {

    public DuplicateProposalException(String message) {
        super(message);
    }
}