package com.fitnesstraining.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;


@Value
@Getter
@Builder
public class CoachSignUpResponse {
    Long userId;
    String username;
    String password;
}
