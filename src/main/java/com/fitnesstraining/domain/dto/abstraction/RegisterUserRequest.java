package com.fitnesstraining.domain.dto.abstraction;

import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class RegisterUserRequest {

    @NotBlank(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String username;

    // todo: should contain different symbols
    @NotBlank(message = ValidationErrorMessages.Password.CANNOT_BE_BLANK)
    @Size(min = 6, max = 100, message = ValidationErrorMessages.Password.SIZE)
    String password;

    @NotBlank(message = ValidationErrorMessages.FirstName.CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ValidationErrorMessages.FirstName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.FirstName.PATTERN)
    String firstName;

    @NotBlank(message = ValidationErrorMessages.LastName.CANNOT_BE_BLANK)
    @Size(min = 2, max = 50, message = ValidationErrorMessages.LastName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.LastName.PATTERN)
    String lastName;

}
