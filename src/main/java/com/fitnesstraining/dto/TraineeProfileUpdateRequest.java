package com.fitnesstraining.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TraineeProfileUpdateRequest {
    Long id;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String address;
}
