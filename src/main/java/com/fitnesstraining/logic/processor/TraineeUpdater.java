package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserRequest;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import com.fitnesstraining.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeUpdater {

    private final TraineeRepository repository;
    private final TraineeMapper mapper;

    private UpdateTraineeRequest request;
    private UUID transactionUuid;
    private Trainee trainee;
    private UpdateTraineeResponse response;


    public synchronized UpdateTraineeResponse update(UpdateUserRequest request) {
        this.request = (UpdateTraineeRequest) request;
        initialLog();

        // check username
        updateTrainee();
        buildResponse();

        finalLog();
        return response;
    }

    // todo: message
    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Updating trainee: {} {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), transactionUuid);
    }

    private void updateTrainee() {
        this.trainee = repository.findByUsername(request.getUsername());

        // todo: check whether new username is taken
        trainee.setUsername(request.getUsername());

        mapper.toEntity(request, trainee);

        repository.update(trainee);
    }

    private void buildResponse() {
        this.response = mapper.toUpdateTraineeResponse(trainee);
    }

    // todo: message
    private void finalLog() {
        log.info("Successfully updated trainee: {} {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), trainee.getId(), transactionUuid);
    }
}
