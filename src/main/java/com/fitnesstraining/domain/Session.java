package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.Duration;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_seq")
    @SequenceGenerator(name = "session_seq", sequenceName = "session_id_seq", allocationSize = 1)
    private Long id;
    private Long coachId;
    private Long traineeId;
    private String name;
    private SessionType type;
    private LocalDateTime date;
    private Duration duration;
}