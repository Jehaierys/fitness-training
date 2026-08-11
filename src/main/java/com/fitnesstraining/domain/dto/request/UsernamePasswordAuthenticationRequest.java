package com.fitnesstraining.domain.dto.request;

import com.fitnesstraining.utils.ValidationErrorMessages;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePasswordAuthenticationRequest {

    @NotNull(message = ValidationErrorMessages.Username.CANNOT_BE_BLANK)
    private String username;

    @NotBlank(message = ValidationErrorMessages.Password.CANNOT_BE_BLANK)
    private String password;

    @Null(message = "IP field must be null here")
    private String ip;

}