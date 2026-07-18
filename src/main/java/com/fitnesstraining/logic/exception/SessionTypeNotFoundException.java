package com.fitnesstraining.logic.exception;

public class SessionTypeNotFoundException extends RuntimeException {

    public SessionTypeNotFoundException(String message) {
        super(message);
    }

    public SessionTypeNotFoundException() {
        super("Session type not found");
    }
}
