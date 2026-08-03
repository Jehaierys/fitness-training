package com.fitnesstraining.utils.entity;

import com.fitnesstraining.domain.entity.SessionType;

// CHECKSTYLE.OFF
public final class SessionTypes {

    private SessionTypes() {}

    public static SessionType yoga() {
        return SessionType.builder()
                .id(1)
                .name("Yoga")
                .build();
    }

    public static SessionType crossfit() {
        return SessionType.builder()
                .id(2)
                .name("Crossfit")
                .build();
    }

    public static SessionType pilates() {
        return SessionType.builder()
                .id(3)
                .name("Pilates")
                .build();
    }

    public static SessionType cardio() {
        return SessionType.builder()
                .id(4)
                .name("Cardio")
                .build();
    }

    public static SessionType strengthTraining() {
        return SessionType.builder()
                .id(5)
                .name("Strength Training")
                .build();
    }

    public static SessionType zumba() {
        return SessionType.builder()
                .id(6)
                .name("Zumba")
                .build();
    }
}