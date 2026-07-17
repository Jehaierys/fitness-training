package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.dto.abstraction.*;
import com.fitnesstraining.domain.dto.trainee.response.RegisterTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeResponse;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.logic.abstraction.TraineeService;
import com.fitnesstraining.logic.abstraction.UserFacade;
import com.fitnesstraining.logic.mapper.TraineeMapper;
import com.fitnesstraining.logic.processor.TraineeRegistrar;
import com.fitnesstraining.logic.processor.TraineeUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TraineeFacade implements UserFacade {

    private final TraineeRegistrar registrar;
    private final TraineeUpdater updater;

    private final TraineeMapper mapper;
    private final TraineeService service;


    public RegisterTraineeResponse register(RegisterUserRequest request) {
        return registrar.register(request);
    }

    public UpdateTraineeResponse update(UpdateUserRequest request) {
        return updater.update(request);
    }

    public GetUserResponse findByUsername(String username) {
        return mapper.toGetTraineeResponse((Trainee) service.findByUsername(username));
    }

    // todo: extract to user scope
    public void setActive(Activated request) {
        final User user = service.findByUsername(request.getUsername());
        user.setActive(request.getIsActive());
        service.update(user);
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