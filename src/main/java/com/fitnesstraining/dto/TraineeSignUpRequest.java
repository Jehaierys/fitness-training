package com.fitnesstraining.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TraineeSignUpRequest {
    String firstName;
    String lastName;
    LocalDate birthDate;
    String address;
}
