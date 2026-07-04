package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeProfileUpdateRequest;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.utils.TraineeSignUpProcessor;
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
    private final TraineeSignUpProcessor signUpProcessor;


    public Trainee signUp(TraineeSignUpRequest request) {
        return signUpProcessor.process(request);
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