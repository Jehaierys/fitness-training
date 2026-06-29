package com.fitnesstraining.service.exception;

public class TraineeNotFoundException extends Throwable {

    public TraineeNotFoundException() {
        super("Trainee not found");
    }

    public TraineeNotFoundException(String s) {
        super(s);
    }
}
