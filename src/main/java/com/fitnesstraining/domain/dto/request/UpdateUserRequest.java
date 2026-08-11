package com.fitnesstraining.domain.dto.request;

import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class UpdateUserRequest {

    @NotBlank(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String username;

    @NotBlank(message = ValidationErrorMessages.FirstName.CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ValidationErrorMessages.FirstName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.FirstName.PATTERN)
    String firstName;

    @NotBlank(message = ValidationErrorMessages.LastName.CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ValidationErrorMessages.LastName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.LastName.PATTERN)
    String lastName;

    @NotNull(message = ValidationErrorMessages.IS_ACTIVE_CANNOT_BE_NULL)
    Boolean isActive;
}