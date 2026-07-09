package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.dto.abstraction.UpdateUserProfileResponse;
import com.fitnesstraining.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpRequest;
import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserFacade;
import com.fitnesstraining.utils.TraineeSignUpProcessor;
import com.fitnesstraining.utils.UpdateTraineeProfileProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade implements UserFacade {

    private final TraineeSignUpProcessor signUpProcessor;
    private final UpdateTraineeProfileProcessor profileUpdateProcessor;


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        return signUpProcessor.process(request);
    }

    @Transactional
    public UpdateTraineeProfileResponse updateProfile(UpdateUserProfileRequest request) {
        return profileUpdateProcessor.process(request);
    }

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