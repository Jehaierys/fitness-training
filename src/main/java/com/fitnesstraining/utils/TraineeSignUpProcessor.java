package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeSignUpProcessor {

    private final UserService userService;
    private final SignUpUtils utils;

    private TraineeSignUpRequest request;
    private UUID logUuid;
    private String password;
    private String username;
    private User user;

    public Trainee process(TraineeSignUpRequest request) {
        this.request = request;
        initialLog();

        generatePassword();
        generateUsername();
        createUser();
        Trainee trainee = createTrainee();

        finalLog();
        return trainee;
    }

    private void initialLog() {
        logUuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), logUuid);
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

    private Trainee createTrainee() {
        return Trainee.builder()
                .user(this.user)
                .address(request.getAddress())
                .birthDate(request.getBirthDate())
                .build();
    }

    private void finalLog() {
        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), user.getId(), logUuid);
    }
}
