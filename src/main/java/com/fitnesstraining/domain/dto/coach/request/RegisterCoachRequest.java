package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserRequest;
import com.fitnesstraining.domain.entity.SessionType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCoachRequest extends RegisterUserRequest {

    // todo: this is not a String
    @NotBlank(message = "Specialization cannot be blank")
    private List<SessionType> specialization;
}
