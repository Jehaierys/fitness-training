package com.fitnesstraining.utils.entity;


import com.fitnesstraining.domain.entity.Session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// todo: remove?
// CHECKSTYLE.OFF
public final class Sessions {

    private Sessions() {}


    public static Session fullAndValid() {
        return Session.builder()
                .id(1L)
                .name("Morning Yoga")
                .duration(Duration.of(1, ChronoUnit.HOURS))
                .sessionType(SessionTypes.yoga())
                .trainee(Users.traineeSophia())
                .date(LocalDateTime.of(2024, 6, 15, 9, 0))
                .coach(Users.coachCarlos())
                .build();
    }
}
