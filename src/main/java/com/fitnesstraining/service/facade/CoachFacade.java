package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachProfileUpdateRequest;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.PasswordGenerator;
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
    private final PasswordGenerator passwordGenerator;

    public synchronized Coach signUp(CoachSignUpRequest request) {
        UUID uuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, specialization: {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), uuid);

        String baseUsername = request.getFirstName().toLowerCase() + "." + request.getLastName().toLowerCase();
        String finalUsername = baseUsername;
        long suffix = 1;

        while (userService.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(finalUsername)
                .password(passwordGenerator.generate())
                .isActive(true)
                .build();
        userService.create(user);

        Coach coach = Coach.builder()
                .user(user)
                .specialization(request.getSpecialization())
                .build();

        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), user.getId(), uuid);

        return coachService.create(coach);
    }

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