package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.*;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.abstraction.TraineeService;
import com.fitnesstraining.logic.abstraction.UserFacade;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import com.fitnesstraining.logic.processor.TraineeRegistrationProcessor;
import com.fitnesstraining.logic.processor.UpdateTraineeProcessor;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade implements UserFacade {

    private final TraineeRegistrationProcessor signUpProcessor;
    private final UpdateTraineeProcessor profileUpdateProcessor;

    private final TraineeMapper mapper;
    private final TraineeService traineeService;


    @Transactional
    public UserSignUpResponse signUp(UserSignUpRequest request) {
        return signUpProcessor.process(request);
    }

    @Transactional
    public UpdateTraineeProfileResponse updateProfile(UpdateUserProfileRequest request) {
        return profileUpdateProcessor.process(request);
    }

    public GetUserResponse findByUsername(String username) {
        return mapper.toGetTraineeResponse((Trainee) traineeService.findByUsername(username));
    }

    @Transactional
    public void setActive(Activated request) {
        User user = traineeService.findByUsername(request.getUsername());
        user.setActive(request.getIsActive());
        traineeService.update(user);
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