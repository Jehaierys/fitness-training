package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.service.abstraction.TraineeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTraineeProfileProcessor {

    private final TraineeService traineeService;

    private UpdateTraineeProfileRequest request;
    private UUID transactionUuid;
    private Trainee trainee;
    private UpdateTraineeProfileResponse response;


    public synchronized UpdateTraineeProfileResponse process(UpdateUserProfileRequest request) {
        this.request = (UpdateTraineeProfileRequest) request;
        initialLog();

        updateTrainee();
        buildResponse();

        finalLog();
        return response;
    }

    // todo: message
    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), transactionUuid);
    }

    private void updateTrainee() {
        this.trainee = (Trainee) traineeService.findByUsername(request.getUsername());

        // todo: check whether new username is taken
        trainee.setUsername(request.getUsername());

        trainee.setFirstName(request.getFirstName());
        trainee.setLastName(request.getLastName());
        trainee.setActive(request.isActive());
        trainee.setBirthDate(request.getBirthDate());
        trainee.setAddress(request.getAddress());

        // todo
        trainee.setCoaches(request.getCoaches());

        traineeService.update(trainee);
    }

    // todo
    private void buildResponse() {
        this.response = UpdateTraineeProfileResponse.builder()
                .username(trainee.getUsername())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .isActive(trainee.isActive())
                .address(trainee.getAddress())
                .birthDate(trainee.getBirthDate())
                .coaches(trainee.getCoaches())
                .build();
    }

    // todo: message
    private void finalLog() {
        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), trainee.getId(), transactionUuid);
    }
}
