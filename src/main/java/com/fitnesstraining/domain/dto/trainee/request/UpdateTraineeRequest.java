package com.fitnesstraining.domain.dto.trainee.request;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTraineeRequest extends UpdateUserRequest {

    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past and represent a realistic age")
    @Schema(description = "Trainee's birth date", example = "1995-05-15", minimum = "1900-01-01")
    LocalDate birthDate;

    @Size(max = 255, message = "Address is too long")
    String address;

}