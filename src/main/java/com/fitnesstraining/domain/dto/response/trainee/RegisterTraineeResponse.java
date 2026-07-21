package com.fitnesstraining.domain.dto.response.trainee;

import com.fitnesstraining.domain.dto.response.RegisterUserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class RegisterTraineeResponse extends RegisterUserResponse {
    // this is ok. All the fields are in UserRegistrationResponse
}
