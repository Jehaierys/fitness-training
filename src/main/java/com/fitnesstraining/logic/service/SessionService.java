package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.dto.request.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.logic.abstraction.SessionTypeService;
import com.fitnesstraining.logic.mapper.SessionMapper;
import com.fitnesstraining.logic.processor.SessionSearcher;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final CoachRepository coachRepository;
    private final TraineeRepository traineeRepository;
    private final SessionTypeService sessionTypeService;
    private final SessionMapper mapper;
    private final SessionSearcher searcher;


    public void create(SessionRegistrationRequest request) {

        final UUID transactionUuid;


        transactionUuid = UUID.randomUUID();
        log.info("Creating new session: {} {}, attempt's UUID: {}",
                request.getTraineeUsername(), request.getCoachUsername(), transactionUuid);


        final Session session = new Session();

        final Trainee trainee = traineeRepository.findByUsername(request.getTraineeUsername());
        session.setTrainee(trainee);
        session.setTrainee(trainee);

        final Coach coach = coachRepository.findByUsername(request.getCoachUsername());
        session.setCoach(coach);
        session.setCoach(coach);

        final SessionType sessionType = sessionTypeService.findByName(request.getSessionTypeName());
        session.setSessionType(sessionType);

        mapper.toEntity(request, session);

        sessionRepository.create(session);
        coachRepository.update(coach);
        traineeRepository.update(trainee);


        log.info("Successfully created session: {} {}, process's UUID: {}",
                request.getTraineeUsername(), request.getCoachUsername(), transactionUuid);
    }


    public List<SessionDto> findSessionsByCriteria(SessionSearchCriteria criteria) {
        return searcher.searchByCriteria(criteria);
    }
}
