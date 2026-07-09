package com.fitnesstraining.utils;

import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.logic.abstraction.TraineeService;
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
    private UserSignUpResponse response;


    public synchronized UserSignUpResponse process(UserSignUpRequest request) {
        this.request = (TraineeSignUpRequest) request;
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
        username = utils.generateUsername(request.getFirstName(), request.getLastName(), traineeService);
    }

    private void createTrainee() {
        this.trainee = (Trainee) traineeService
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
        this.response = UserSignUpResponse.builder()
                .userId(trainee.getId())
                .username(trainee.getUsername())
                .password(trainee.getPassword())
                .build();
    }

    private void finalLog() {
        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), trainee.getId(), traineeUuid);
    }
}
