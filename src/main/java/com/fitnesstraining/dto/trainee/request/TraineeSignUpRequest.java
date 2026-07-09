package com.fitnesstraining.dto.trainee.request;

import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class TraineeSignUpRequest extends UserSignUpRequest {
    LocalDate birthDate;
    String address;
}
