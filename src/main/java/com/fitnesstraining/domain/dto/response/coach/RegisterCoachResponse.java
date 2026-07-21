package com.fitnesstraining.domain.dto.response.coach;

import com.fitnesstraining.domain.dto.response.RegisterUserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RegisterCoachResponse extends RegisterUserResponse {
    // this is ok. All the fields are in UserRegistrationResponse
}
