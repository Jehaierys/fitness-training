package com.fitnesstraining.logic.processor;


import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.coach.request.UpdateCoachProfileRequest;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.mapper.CoachMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCoachProfileProcessor {

    private final CoachService coachService;
    private final CoachMapper mapper;

    private UpdateCoachProfileRequest request;
    private UUID transactionUuid;
    private Coach coach;
    private UpdateCoachProfileResponse response;


    public synchronized UpdateCoachProfileResponse process(UpdateUserProfileRequest request) {
        this.request = (UpdateCoachProfileRequest) request;
        initialLog();

        updateCoach();
        buildResponse();

        finalLog();
        return response;
    }

    // todo: message
    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, specialization: {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), transactionUuid);
    }

    private void updateCoach() {
        this.coach = (Coach) coachService.findByUsername(request.getUsername());

        // todo: check whether new username is taken
        coach.setUsername(request.getUsername());

        mapper.toEntity(request, coach);

        coachService.update(coach);
    }

    private void buildResponse() {
        this.response = mapper.toUpdateCoachProfileResponse(coach);
    }

    // todo: message
    private void finalLog() {
        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), coach.getId(), transactionUuid);
    }
}
