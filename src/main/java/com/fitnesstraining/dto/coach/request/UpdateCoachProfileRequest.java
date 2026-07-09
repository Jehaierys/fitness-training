package com.fitnesstraining.dto.coach.request;

import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
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
