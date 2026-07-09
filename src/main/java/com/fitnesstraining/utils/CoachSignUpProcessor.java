package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.dto.CoachSignUpResponse;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
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
    private CoachSignUpResponse response;


    public synchronized CoachSignUpResponse process(CoachSignUpRequest request) {
        this.request = request;
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
        username = utils.generateUsername(request.getFirstName(), request.getLastName(), (UserService) coachService);
    }

    private void createCoach() {
        this.coach = coachService
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
        this.response = CoachSignUpResponse.builder()
                .userId(coach.getId())
                .username(username)
                .password(password)
                .build();
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), coach.getId(), transactionUuid);
    }
}
