package com.riwi.projectmanager.domain.exception;

public class InvalidTaskStateException extends BusinessException {
    public InvalidTaskStateException(String message) {
        super(message);
    }
}
