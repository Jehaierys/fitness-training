package com.fitnesstraining.domain.dto.coach.response;

import com.fitnesstraining.domain.dto.abstraction.GetUserResponse;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class GetCoachResponse extends GetUserResponse {

    private Set<SessionType> specialization;
    // todo: fn, ln, username
    private Set<Trainee> trainees;
}
