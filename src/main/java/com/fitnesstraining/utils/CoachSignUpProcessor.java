package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoachSignUpProcessor {

    private final UserService userService;
    private final SignUpUtils utils;

    private CoachSignUpRequest request;
    private UUID logUuid;
    private String password;
    private String username;
    private User user;


    public Coach process(CoachSignUpRequest request) {
        this.request = request;
        initialLog();

        generatePassword();
        generateUsername();
        createUser();
        Coach coach = createCoach();

        finalLog();
        return coach;
    }

    private void initialLog() {
        logUuid = UUID.randomUUID();
        log.info("Signing up new coach: {} {}, specialization: {}, attempt's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), logUuid);
    }

    private void generatePassword() {
        password = utils.generatePassword();
    }

    private void generateUsername() {
        username = utils.generateUsername(request.getFirstName(), request.getLastName());
    }

    private void createUser() {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();

        this.user = userService.create(user);
    }

    private Coach createCoach() {
        return Coach.builder()
                .user(this.user)
                .specialization(request.getSpecialization())
                .build();
    }

    private void finalLog() {
        log.info("Successfully created coach: {} {}, specialization: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getSpecialization(), user.getId(), logUuid);
    }
}
