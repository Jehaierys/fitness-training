package com.fitnesstraining.domain.dto.trainee.response;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class UpdateTraineeProfileResponse extends UpdateUserProfileResponse {

    private LocalDate birthDate;
    private String address;

    // todo: cut
    private Set<Coach> coaches;
}
