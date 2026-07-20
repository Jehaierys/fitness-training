package com.fitnesstraining.domain.dto.trainee.request;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserRequest;
import com.fitnesstraining.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RegisterTraineeRequest extends RegisterUserRequest {

    @Past(message = "Birth date must be in the past and represent a realistic age")
    @Schema(description = "Trainee's birth date", example = "1990-01-01", minimum = "1900-01-01")
    LocalDate birthDate;

    @Size(min = 5, max = 255, message = ValidationErrorMessages.Address.SIZE)
    String address;
}

