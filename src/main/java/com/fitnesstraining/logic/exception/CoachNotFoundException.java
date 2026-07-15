package com.fitnesstraining.logic.exception;

public class CoachNotFoundException extends RuntimeException {

    public CoachNotFoundException() {
        super("Coach not found");
    }

    public CoachNotFoundException(String message) {
        super(message);
    }
}