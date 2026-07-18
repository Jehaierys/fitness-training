package com.fitnesstraining.logic.exception;

public class TraineeNotFoundException extends RuntimeException {

    public TraineeNotFoundException() {
        super("Trainee not found");
    }

    public TraineeNotFoundException(String message) {
        super(message);
    }
}