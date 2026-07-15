package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachProfileUpdateRequest;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.CoachSignUpProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;



@Slf4j
@Component
@RequiredArgsConstructor
public class CoachFacade {

    private final UserService userService;
    private final CoachService coachService;
    private final CoachSignUpProcessor signUpProcessor;


    @Transactional
    public synchronized Coach signUp(CoachSignUpRequest request) {
        Coach coach = signUpProcessor.process(request);
        return coachService.create(coach);
    }


    @Transactional
    public Coach updateProfile(CoachProfileUpdateRequest request) {
        UUID uuid = UUID.randomUUID();
        log.info("Updating coach profile for id: {}, process's UUID: {}", request.getId(), uuid);

        Coach existingCoach = coachService.getById(request.getId());
        User existingUser = existingCoach.getUser();

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        userService.update(existingUser);

        existingCoach.setSpecialization(request.getSpecialization());
        coachService.update(existingCoach);

        log.info("Successfully updated coach profile for id: {}, process's UUID: {}", request.getId(), uuid);
        return existingCoach;
    }
}