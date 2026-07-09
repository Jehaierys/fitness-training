package com.fitnesstraining.dto.trainee.response;

import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TraineeSignUpResponse extends UserSignUpResponse {
    // this os ok. All the fields are in UserSignUpResponse
}
