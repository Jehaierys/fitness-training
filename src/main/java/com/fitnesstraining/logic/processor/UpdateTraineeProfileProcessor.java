package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.logic.abstraction.TraineeService;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTraineeProfileProcessor {

    private final TraineeService traineeService;
    private final TraineeMapper mapper;

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

        mapper.toEntity(request, trainee);

        traineeService.update(trainee);
    }

    private void buildResponse() {
        this.response = mapper.toUpdateTraineeProfileResponse(trainee);
    }

    // todo: message
    private void finalLog() {
        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), trainee.getId(), transactionUuid);
    }
}
