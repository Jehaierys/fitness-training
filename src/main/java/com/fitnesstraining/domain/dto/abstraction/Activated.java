package com.fitnesstraining.domain.dto.abstraction;

import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Activated {

    @NotBlank(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String username;

    @NotNull(message = ValidationErrorMessages.IS_ACTIVE_CANNOT_BE_NULL)
    Boolean isActive;
}
