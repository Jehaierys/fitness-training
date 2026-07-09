package com.fitnesstraining.domain.dto.session;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class SessionSearchCriteria {
    String traineeUsername;
    String coachUsername;
    LocalDate fromDate;
    LocalDate toDate;
    String coachFirstName;
    String coachLastName;
    String sessionType;
}
