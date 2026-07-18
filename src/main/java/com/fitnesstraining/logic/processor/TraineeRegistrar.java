package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserRequest;
import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;
import com.fitnesstraining.domain.dto.trainee.response.RegisterTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import com.fitnesstraining.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeRegistrar {

    private final TraineeMapper mapper;
    private final TraineeRepository repository;
    private final PasswordEncoder passwordEncoder;

    private RegisterTraineeRequest request;
    private UUID traineeUuid;
    private Trainee trainee;
    private RegisterTraineeResponse response;


    public synchronized RegisterTraineeResponse register(RegisterUserRequest request) {
        this.request = (RegisterTraineeRequest) request;
        initialLog();

        // check user exists by username
        createTrainee();
        buildResponse();

        finalLog();
        return response;
    }

    private void initialLog() {
        traineeUuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}",
                request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), traineeUuid);
    }

    private void createTrainee() {
        trainee = new Trainee();
        mapper.toEntity(request, trainee);

        trainee.setPassword(passwordEncoder.encode(request.getPassword()));

        trainee.setActive(true);

        trainee = repository.create(trainee);
    }

    private void buildResponse() {
        this.response = mapper.toRegisterTraineeResponse(trainee);
    }

    private void finalLog() {
        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), trainee.getId(), traineeUuid);
    }
}
