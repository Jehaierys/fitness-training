package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeProfileUpdateRequest;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;



@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private final UserService userService;
    private final TraineeService traineeService;
    private final PasswordGenerator passwordGenerator;

    public Trainee signUp(TraineeSignUpRequest request) {
        UUID uuid = UUID.randomUUID();
        log.info("Signing up new trainee: {} {}, birth date: {}, address: {}, process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), uuid);

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

        Trainee trainee = Trainee.builder()
                .user(user)
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .build();

        log.info("Successfully created trainee: {} {}, birth date: {}, address: {}, userId: {} process's UUID: {}", request.getFirstName(), request.getLastName(), request.getBirthDate(), request.getAddress(), user.getId(), uuid);

        return traineeService.create(trainee);
    }

    public Trainee updateProfile(TraineeProfileUpdateRequest request) {
        UUID uuid = UUID.randomUUID();
        log.info("Updating trainee profile for id: {}, process's UUID: {}", request.getId(), uuid);

        Trainee existingTrainee = traineeService.getById(request.getId());
        User existingUser = existingTrainee.getUser();

        existingUser.setFirstName(request.getFirstName());
        existingUser.setLastName(request.getLastName());
        userService.update(existingUser);

        existingTrainee.setBirthDate(request.getBirthDate());
        existingTrainee.setAddress(request.getAddress());
        traineeService.update(existingTrainee);

        log.info("Successfully updated trainee profile for id: {}, process's UUID: {}", request.getId(), uuid);
        return existingTrainee;
    }
}