package com.fitnesstraining.utils;

import com.fitnesstraining.service.exception.*;

import java.util.function.Supplier;

public class ExceptionSuppliers {

    public static Supplier<CoachNotFoundException> CoachNotFound(String message) {
        return () -> new CoachNotFoundException(message);
    }

    public static Supplier<SessionNotFoundException> SessionNotFound(String message) {
        return () -> new SessionNotFoundException(message);
    }

    public static Supplier<UserNotFoundException> UserNotFound(String message) {
        return () -> new UserNotFoundException(message);
    }

    public static Supplier<TraineeNotFoundException> TraineeNotFound(String message) {
        return () -> new TraineeNotFoundException(message);
    }

    public static Supplier<SessionTypeNotFoundException> SessionTypeNotFound(String message) {
        return () -> new SessionTypeNotFoundException(message);
    }
}
