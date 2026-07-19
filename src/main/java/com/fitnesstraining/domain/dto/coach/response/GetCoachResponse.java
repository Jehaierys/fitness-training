package com.fitnesstraining.domain.dto.coach.response;

import com.fitnesstraining.domain.dto.abstraction.GetUserResponse;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetCoachResponse extends GetUserResponse {

    Set<SessionType> specialization;
    // todo: fn, ln, username
    Set<Trainee> trainees;
}
