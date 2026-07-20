package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserRequest;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterCoachRequest extends RegisterUserRequest {

    // todo: this is not a String
    @NotEmpty(message = ValidationErrorMessages.SPECIALIZATION_CANNOT_BE_EMPTY)
    List<SessionType> specialization;
}
