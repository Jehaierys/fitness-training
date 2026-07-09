package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.Session;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.dto.SessionRegistrationRequest;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.SessionService;
import com.fitnesstraining.service.abstraction.SessionTypeService;
import com.fitnesstraining.service.abstraction.TraineeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionFacade {

    private final TraineeService traineeService;
    private final CoachService coachService;
    private final SessionService sessionService;
    private final SessionTypeService sessionTypeService;


    @Transactional
    public Session registerSession(SessionRegistrationRequest request) {
        Trainee trainee = (Trainee) traineeService.getById(request.getTraineeId());
        Coach coach = (Coach) coachService.getById(request.getCoachId());
        SessionType sessionType = sessionTypeService.getById(request.getSessionTypeId());

        Session session = Session.builder()
                .trainee(trainee)
                .coach(coach)
                .sessionType(sessionType)
                .name(request.getName())
                .date(request.getDate())
                .duration(request.getDuration())
                .build();

        return sessionService.create(session);
    }
}
