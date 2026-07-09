package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class UpdateCoachProfileRequest extends UpdateUserProfileRequest {

    Set<SessionType> specialization;
    Set<Trainee> trainees;
}
