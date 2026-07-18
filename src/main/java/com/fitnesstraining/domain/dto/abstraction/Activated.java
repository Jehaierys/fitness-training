package com.fitnesstraining.domain.dto.abstraction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Activated {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Username can only contain letters, numbers, dots, and underscores")
    String username;

    @NotNull
    Boolean isActive;
}
