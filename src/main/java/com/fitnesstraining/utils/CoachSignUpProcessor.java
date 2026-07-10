package com.fitnesstraining.utils;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.logic.abstraction.CoachService;
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
    private final CoachMapper mapper;

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
        log.info("Signing up new coach: {} {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), transactionUuid);
    }

    private void generatePassword() {
        password = utils.generatePassword();
    }

    private void generateUsername() {
        username = utils.generateUsername(request.getFirstName(), request.getLastName(), coachService);
    }

    private void createCoach() {
        this.coach = new Coach();
        mapper.toEntity(request, coach);

        coach.setPassword(password);
        coach.setUsername(username);

        coach.setActive(true);

        coachService.create(coach);
    }

    private void buildResponse() {
        this.response = mapper.toUserSignUpResponse(coach);
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), coach.getId(), transactionUuid);
    }
}
