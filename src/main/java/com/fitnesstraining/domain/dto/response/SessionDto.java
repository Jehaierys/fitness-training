package com.fitnesstraining.domain.dto.response;

import com.fitnesstraining.domain.entity.SessionType;

import java.time.Duration;
import java.time.LocalDateTime;

// no ids
public class SessionDto {

    String sessionName;
    LocalDateTime date;
    SessionType sessionType;
    Duration duration;

    String traineeUsername;
    String traineeFirstName;
    String traineeLastName;

    String coachUsername;
    String coachFirstName;
    String coachLastName;
}
