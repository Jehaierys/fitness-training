package com.fitnesstraining.dto.coach.response;

import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileResponse;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@SuperBuilder
public class UpdateCoachProfileResponse extends UpdateUserProfileResponse {

    private Set<SessionType> specialization;
    // todo: fn, ln, username
    private Set<Trainee> trainees;
}
