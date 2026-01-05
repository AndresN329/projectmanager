package com.riwi.projectmanager.domain.exception;

public class InvalidProjectStateException extends RuntimeException {

    public InvalidProjectStateException(String message) {
        super(message);
    }
}
