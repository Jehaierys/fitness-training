package com.fitnesstraining.utils;

import com.fitnesstraining.service.exception.TraineeNotFoundException;
import com.fitnesstraining.service.exception.UserNotFoundException;

public class ExceptionThrowers {

    public static void UserNotFound(String message) {
        throw new UserNotFoundException(message);
    }

    public static void TraineeNotFound(String message) {
        throw new TraineeNotFoundException(message);

    }
}
