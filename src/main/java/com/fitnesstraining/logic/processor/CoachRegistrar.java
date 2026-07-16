package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.dto.abstraction.RegisterUserRequest;
import com.fitnesstraining.domain.dto.coach.request.RegisterCoachRequest;
import com.fitnesstraining.domain.dto.coach.response.RegisterCoachResponse;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.logic.mapper.CoachMapper;
import com.fitnesstraining.repository.CoachRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoachRegistrar {

    private final CoachRepository repository;
    private final CoachMapper mapper;

    private RegisterCoachRequest request;
    private UUID transactionUuid;
    private Coach coach;
    private RegisterCoachResponse response;


    public synchronized RegisterCoachResponse register(RegisterUserRequest request) {
        this.request = (RegisterCoachRequest) request;
        initialLog();

        // check user exists by username
        createCoach();
        buildResponse();

        finalLog();
        return response;
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, attempt's UUID: {}",
                request.getFirstName(), request.getLastName(), transactionUuid);
    }

    private void createCoach() {
        this.coach = new Coach();
        mapper.toEntity(request, coach);

        coach.setActive(true);

        coach = repository.create(coach);
    }

    private void buildResponse() {
        this.response = mapper.toRegisterCoachResponse(coach);
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, userId: {} process's UUID: {}",
                request.getFirstName(), request.getLastName(), coach.getId(), transactionUuid);
    }
}
