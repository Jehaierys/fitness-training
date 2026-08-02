package com.fitnesstraining.domain.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernamePasswordAuthenticationRequest {

    @NotNull(message = "Username cannot be null")
    private String username;

    @NotNull(message = "Password cannot be null")
    private String password;

    @Nullable
    private String ip;

}