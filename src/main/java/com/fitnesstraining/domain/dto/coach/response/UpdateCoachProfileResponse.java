package com.fitnesstraining.domain.dto.coach.response;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileResponse;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@SuperBuilder
public class UpdateCoachProfileResponse extends UpdateUserProfileResponse {

    // todo
    private List<SessionType> specialization;
    // todo: fn, ln, username
    private Set<Trainee> trainees;
}
