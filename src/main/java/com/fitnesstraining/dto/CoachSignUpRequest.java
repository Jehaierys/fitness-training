package com.fitnesstraining.dto;

import com.fitnesstraining.domain.SessionType;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CoachSignUpRequest {
    String firstName;
    String lastName;
    Set<SessionType> specialization;
}
