package com.fitnesstraining.domain.dto.request.session;

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

    @NotBlank(message = "Trainee username cannot be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, dots, and underscores")
    String traineeUsername;

    @NotBlank(message = "Coach username cannot be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, dots, and underscores")
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