package com.fitnesstraining.utils;


import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.dto.coach.request.UpdateCoachProfileRequest;
import com.fitnesstraining.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.service.abstraction.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCoachProfileProcessor {

    private final CoachService coachService;

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

        coach.setFirstName(request.getFirstName());
        coach.setLastName(request.getLastName());
        coach.setSpecialization(request.getSpecialization());
        coach.setActive(request.isActive());

        coachService.update(coach);
    }

    private void buildResponse() {
        this.response = UpdateCoachProfileResponse.builder()
                .username(coach.getUsername())
                .firstName(coach.getFirstName())
                .lastName(coach.getLastName())
                .specialization(coach.getSpecialization())
                .isActive(coach.isActive())
                .trainees(coach.getTrainees())
                .build();
    }

    // todo: message
    private void finalLog() {
        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), coach.getId(), transactionUuid);
    }
}
