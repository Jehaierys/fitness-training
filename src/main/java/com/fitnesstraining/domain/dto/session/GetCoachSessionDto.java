package com.fitnesstraining.domain.dto.session;

import com.fitnesstraining.domain.entity.SessionType;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetCoachSessionDto {

    String name;
    LocalDateTime date;
    SessionType sessionType;
    Duration duration;
    String traineeFirstName;
    String traineeLastName;
}
