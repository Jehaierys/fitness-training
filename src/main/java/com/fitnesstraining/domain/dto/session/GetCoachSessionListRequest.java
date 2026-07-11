package com.fitnesstraining.domain.dto.session;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetCoachSessionListRequest {

    @NotBlank(message = "Coach username cannot be blank")
    String coachUsername;

    @NotNull(message = "From date cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime from;

    @NotNull(message = "To date cannot be null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime to;

    // optional
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "First name can only contain letters, hyphens, and spaces")
    String traineeFirstName;

    // optional
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "First name can only contain letters, hyphens, and spaces")
    String traineeLastName;
}
