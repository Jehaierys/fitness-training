package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.dto.coach.response.CoachSignUpResponse;
import com.fitnesstraining.service.abstraction.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoachSignUpProcessor {

    private final CoachService coachService;
    private final SignUpUtils utils;

    private CoachSignUpRequest request;
    private UUID transactionUuid;
    private String password;
    private String username;
    private Coach coach;
    private UserSignUpResponse response;


    public synchronized UserSignUpResponse process(UserSignUpRequest request) {
        this.request = (CoachSignUpRequest) request;
        initialLog();

        generatePassword();
        generateUsername();
        createCoach();
        buildResponse();

        finalLog();
        return response;
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, specialization: {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), transactionUuid);
    }

    private void generatePassword() {
        password = utils.generatePassword();
    }

    private void generateUsername() {
        username = utils.generateUsername(request.getFirstName(), request.getLastName(), coachService);
    }

    private void createCoach() {
        this.coach = (Coach) coachService
                .create(Coach.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .username(username)
                        .password(password)
                        .isActive(true)
                        .specialization(request.getSpecialization())
                        .build()
                );
    }

    private void buildResponse() {
        this.response = UserSignUpResponse.builder()
                .userId(coach.getId())
                .username(username)
                .password(password)
                .build();
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), coach.getId(), transactionUuid);
    }
}
