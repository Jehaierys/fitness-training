package com.fitnesstraining.service.exception;

public class UserNotFoundException extends Throwable {

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
