package com.fitnesstraining.logic.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
        super("Coach not found");
    }

    public SessionNotFoundException(String message) {
        super(message);
    }
}
