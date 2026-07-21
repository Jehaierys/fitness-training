package com.fitnesstraining.testUtils.entity;

import com.fitnesstraining.domain.entity.SessionType;

// CHECKSTYLE.OFF
public final class SessionTypes {

    private SessionTypes() {}

    public static SessionType yoga() {
        return SessionType.builder()
                .id(1L)
                .name("Yoga")
                .build();
    }

    public static SessionType crossfit() {
        return SessionType.builder()
                .id(2L)
                .name("Crossfit")
                .build();
    }

    public static SessionType pilates() {
        return SessionType.builder()
                .id(3L)
                .name("Pilates")
                .build();
    }

    public static SessionType cardio() {
        return SessionType.builder()
                .id(4L)
                .name("Cardio")
                .build();
    }

    public static SessionType strengthTraining() {
        return SessionType.builder()
                .id(5L)
                .name("Strength Training")
                .build();
    }

    public static SessionType zumba() {
        return SessionType.builder()
                .id(6L)
                .name("Zumba")
                .build();
    }
}