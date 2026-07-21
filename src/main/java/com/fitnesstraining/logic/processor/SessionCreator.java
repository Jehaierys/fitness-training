package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.abstraction.SessionService;
import com.fitnesstraining.logic.abstraction.SessionTypeService;
import com.fitnesstraining.logic.abstraction.TraineeService;
import com.fitnesstraining.logic.mapper.SessionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component@RequiredArgsConstructor
public class SessionCreator {

    private final TraineeService traineeService;
    private final CoachService coachService;
    private final SessionService sessionService;
    private final SessionTypeService sessionTypeService;
    private final SessionMapper mapper;

    private SessionRegistrationRequest request;
    private UUID transactionUuid;


    public synchronized void create(SessionRegistrationRequest request) {
        this.request = request;
        initialLog();

        buildSession();

        finalLog();
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Creating new session: {} {}, attempt's UUID: {}",
                request.getTraineeUsername(), request.getCoachUsername(), transactionUuid);
    }

    private void buildSession() {
        final Session session = new Session();

        final Trainee trainee = (Trainee) traineeService.findByUsername(request.getTraineeUsername());
        session.setTrainee(trainee);

        final Coach coach = (Coach) coachService.findByUsername(request.getCoachUsername());
        session.setCoach(coach);

        final SessionType sessionType = sessionTypeService.findByName(request.getSessionTypeName());
        session.setSessionType(sessionType);

        mapper.toEntity(request, session);

        sessionService.create(session);
    }

    private void finalLog() {
        log.info("Successfully created session: {} {}, process's UUID: {}",
                request.getTraineeUsername(), request.getCoachUsername(), transactionUuid);
    }
}
