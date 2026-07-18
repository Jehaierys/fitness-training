package com.fitnesstraining.domain.dto.coach.response;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RegisterCoachResponse extends RegisterUserResponse {
    Long userId;
    String username;
    String password;
}
