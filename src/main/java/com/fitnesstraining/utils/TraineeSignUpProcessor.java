package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.TraineeSignUpResponse;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeSignUpProcessor {

    private final SignUpUtils utils;
    private final TraineeService traineeService;

    private TraineeSignUpRequest request;
    private UUID traineeUuid;
    private String password;
    private String username;
    private Trainee trainee;
    private TraineeSignUpResponse response;


    public synchronized TraineeSignUpResponse process(TraineeSignUpRequest request) {
        this.request = request;
        initialLog();

        generatePassword();
        generateUsername();
        createTrainee();
        buildResponse();

        finalLog();
        return response;
    }

    private void initialLog() {
        traineeUuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), traineeUuid);
    }

    private void generatePassword() {
        password = utils.generatePassword();
    }

    private void generateUsername() {
        username = utils.generateUsername(request.getFirstName(), request.getLastName(), (UserService) traineeService);
    }

    private void createTrainee() {
        this.trainee = traineeService
                .create(Trainee.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .username(username)
                        .password(password)
                        .isActive(true)
                        .address(request.getAddress())
                        .birthDate(request.getBirthDate())
                        .build()
                );
    }

    private void buildResponse() {
        this.response = TraineeSignUpResponse.builder()
                .userId(trainee.getId())
                .username(trainee.getUsername())
                .password(trainee.getPassword())
                .build();
    }

    private void finalLog() {
        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), trainee.getId(), traineeUuid);
    }
}
