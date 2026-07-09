package com.fitnesstraining.logic.facade;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.abstraction.SessionService;
import com.fitnesstraining.logic.abstraction.SessionTypeService;
import com.fitnesstraining.logic.abstraction.TraineeService;
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
