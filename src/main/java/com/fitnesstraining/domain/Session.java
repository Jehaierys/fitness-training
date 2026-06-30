package com.fitnesstraining.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.Duration;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private Long id;
    private Long coachId;
    private Long traineeId;
    private String name;
    private SessionType type;
    private LocalDateTime date;
    private Duration duration;
}