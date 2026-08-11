package com.fitnesstraining.domain.dto.request.session;

import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionRegistrationRequest {

    @NotBlank(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String traineeUsername;

    @NotBlank(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String coachUsername;

    @NotBlank(message = "Session type name cannot be blank")
    @Size(min = 2, max = 50, message = "Session type name must be between 2 and 50 characters")
    String sessionTypeName;

    @NotBlank(message = "Session name cannot be blank")
    @Size(min = 2, max = 100, message = "Session name must be between 2 and 100 characters")
    String name;

    @NotNull(message = "Session date cannot be null")
    @Future(message = "Session date must be in the future")
    LocalDateTime date;

    @NotNull(message = "Session duration cannot be null")
    Duration duration;
}