package com.fitnesstraining.dto.trainee.request;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class UpdateTraineeProfileRequest extends UpdateUserProfileRequest {

    private LocalDate birthDate;
    private String address;
    // todo: cut
    private Set<Coach> coaches;
}
