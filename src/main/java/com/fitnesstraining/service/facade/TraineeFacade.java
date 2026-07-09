package com.fitnesstraining.service.facade;

import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.dto.TraineeSignUpResponse;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.utils.TraineeSignUpProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade {

    private final TraineeService traineeService;
    private final TraineeSignUpProcessor signUpProcessor;
    private final CoachService coachService;


    @Transactional
    public TraineeSignUpResponse signUp(TraineeSignUpRequest request) {
        return signUpProcessor.process(request);
    }
//
//    @Transactional
//    public Trainee updateProfile(TraineeProfileUpdateRequest request) {
//        UUID uuid = UUID.randomUUID();
//        log.info("Updating trainee profile for id: {}, process's UUID: {}", request.getId(), uuid);
//
//        Trainee existingTrainee = traineeService.getById(request.getId());
//        User existingUser = existingTrainee.getUser();
//
//        existingUser.setFirstName(request.getFirstName());
//        existingUser.setLastName(request.getLastName());
//        userService.update(existingUser);
//
//        existingTrainee.setBirthDate(request.getBirthDate());
//        existingTrainee.setAddress(request.getAddress());
//        traineeService.update(existingTrainee);
//
//        log.info("Successfully updated trainee profile for id: {}, process's UUID: {}", request.getId(), uuid);
//        return existingTrainee;
//    }
//
//    @Transactional
//    public Trainee updateCoaches(UpdateTraineeCoachesRequest request) {
//        Trainee trainee = traineeService.getById(request.getTraineeId());
//
//        Set<Coach> newCoaches = new HashSet<>();
//        request.getCoachIds().forEach(coachId -> {
//            Coach coach = coachService.getById(coachId);
//            newCoaches.add(coach);
//        });
//
//        trainee.setCoaches(newCoaches);
//        return traineeService.update(trainee);
//    }
}