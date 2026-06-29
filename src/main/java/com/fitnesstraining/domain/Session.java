package com.fitnesstraining.domain;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.Duration;


@Getter
@Setter
public class Session {
    private Long id;
    private Long coachId;
    private Long traineeId;
    private String name;
    private SessionType type;
    private LocalDateTime date;
    private Duration duration;
}