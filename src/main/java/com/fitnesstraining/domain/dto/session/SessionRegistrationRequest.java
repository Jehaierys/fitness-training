package com.fitnesstraining.domain.dto.session;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.Duration;

@Value
@Builder
public class SessionRegistrationRequest {
    Long traineeId;
    Long coachId;
    Long sessionTypeId;
    String name;
    LocalDateTime date;
    Duration duration;
}