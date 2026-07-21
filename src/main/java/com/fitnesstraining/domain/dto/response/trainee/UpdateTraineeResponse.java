package com.fitnesstraining.domain.dto.response.trainee;

import com.fitnesstraining.domain.dto.response.UpdateUserResponse;
import com.fitnesstraining.domain.entity.Coach;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
public class UpdateTraineeResponse extends UpdateUserResponse {

    private LocalDate birthDate;
    private String address;

    // todo: cut
    private Set<Coach> coaches;
}
