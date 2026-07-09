package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class CoachSignUpRequest extends UserSignUpRequest {
    Set<SessionType> specialization;
}
