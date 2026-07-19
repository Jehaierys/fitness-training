package com.fitnesstraining.domain.dto.trainee.response;

import com.fitnesstraining.domain.dto.abstraction.GetUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetTraineeResponse extends GetUserResponse {
    String address;
    LocalDate birthDate;
}
