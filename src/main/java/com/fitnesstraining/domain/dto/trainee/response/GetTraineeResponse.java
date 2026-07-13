package com.fitnesstraining.domain.dto.trainee.response;

import com.fitnesstraining.domain.dto.abstraction.GetUserResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetTraineeResponse extends GetUserResponse {
    String address;
    LocalDate birthDate;
}
