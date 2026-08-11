package com.fitnesstraining.utils;

import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.response.coach.RegisterCoachResponse;
import com.fitnesstraining.domain.dto.request.trainee.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.request.trainee.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.response.trainee.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.response.trainee.UpdateTraineeResponse;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class TestMapper {

    public RegisterCoachResponse toResponse(RegisterCoachRequest request) {
        return RegisterCoachResponse.builder()
                .userId(100L)
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public RegisterTraineeResponse toResponse(RegisterTraineeRequest request) {
        return RegisterTraineeResponse.builder()
                .userId(200L)
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    public UpdateTraineeResponse toResponse(UpdateTraineeRequest request) {
        return UpdateTraineeResponse.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .build();
    }
}
