package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;


@Slf4j
@Component
@RequiredArgsConstructor
public class CoachFacade {

    private final UserService userService;
    private final CoachService coachService;
    private final PasswordGenerator passwordGenerator;

    public Coach signUp(
            String firstName,
            String lastName,
            Set<SessionType> specialization
    ) {
        UUID uuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, specialization: {}, attempt's UUID: {}", firstName, lastName, specialization, uuid);

        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String finalUsername = baseUsername;
        long suffix = 1;

        while (userService.existsByUsername(finalUsername)) {
            finalUsername = baseUsername + suffix;
            suffix++;
        }

        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(finalUsername)
                .password(passwordGenerator.generate())
                .isActive(true)
                .build();
        userService.create(user);

        Coach coach = Coach.builder()
                .user(user)
                .specialization(specialization)
                .build();

        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", firstName, lastName, specialization, user.getId(), uuid);

        return coachService.create(coach);
    }
}