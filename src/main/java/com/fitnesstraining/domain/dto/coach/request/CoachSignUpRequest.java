package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CoachSignUpRequest extends UserSignUpRequest {
    Set<SessionType> specialization;
}
