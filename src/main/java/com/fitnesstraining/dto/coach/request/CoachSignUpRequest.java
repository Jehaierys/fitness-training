package com.fitnesstraining.dto.coach.request;

import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CoachSignUpRequest extends UserSignUpRequest {
    Set<SessionType> specialization;
}
