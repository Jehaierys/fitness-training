package com.fitnesstraining.dto.coach.response;

import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CoachSignUpResponse extends UserSignUpRequest {
    Long userId;
    String username;
    String password;
}
