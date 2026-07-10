package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.entity.SessionType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
public class CoachSignUpRequest extends UserSignUpRequest {

    // todo: this is not a String
    @NotBlank(message = "Specialization cannot be blank")
    private List<SessionType> specialization;
}
