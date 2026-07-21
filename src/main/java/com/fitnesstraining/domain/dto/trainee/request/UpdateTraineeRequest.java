package com.fitnesstraining.domain.dto.trainee.request;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserRequest;
import com.fitnesstraining.utils.ValidationErrorMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateTraineeRequest extends UpdateUserRequest {

    @Past(message = ValidationErrorMessages.BirthDate.PAST)
    @Schema(description = "Trainee's birth date", example = "1995-05-15", minimum = "1900-01-01")
    LocalDate birthDate;

    @Size(min = 5, max = 255, message = ValidationErrorMessages.Address.SIZE)
    String address;

}