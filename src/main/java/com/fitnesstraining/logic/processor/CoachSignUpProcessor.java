package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.mapper.CoachMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoachSignUpProcessor {

    private final CoachService coachService;
    private final CoachMapper mapper;

    private CoachSignUpRequest request;
    private UUID transactionUuid;
    private Coach coach;
    private UserSignUpResponse response;


    public synchronized UserSignUpResponse process(UserSignUpRequest request) {
        this.request = (CoachSignUpRequest) request;
        initialLog();

        // check user exists by username
        createCoach();
        buildResponse();

        finalLog();
        return response;
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), transactionUuid);
    }

    private void createCoach() {
        this.coach = new Coach();
        mapper.toEntity(request, coach);

        coach.setActive(true);

        coach = (Coach) coachService.create(coach);
    }

    private void buildResponse() {
        this.response = mapper.toUserSignUpResponse(coach);
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), coach.getId(), transactionUuid);
    }
}
