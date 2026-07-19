package com.fitnesstraining.domain.dto.abstraction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
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
public abstract class UpdateUserRequest {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, dots, and underscores")
    String username;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "First name can only contain letters, hyphens, and spaces")
    String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "Last name can only contain letters, hyphens, and spaces")
    String lastName;

    @NotNull(message = "Active status must be specified")
    Boolean isActive;
}