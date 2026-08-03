package com.fitnesstraining.service.utils;

import com.fitnesstraining.domain.dto.request.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.response.SessionDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.service.mapper.SessionMapper;
import com.fitnesstraining.repository.dsl.Criteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionSearcher {

    // don't use entityManager in this class
    private final EntityManager entityManager;
    private final SessionMapper mapper;


    public List<SessionDto> searchByCriteria(SessionSearchCriteria request) {


        final UUID transactionUuid;
        final List<Session> sessions;
        final List<SessionDto> response;

        // todo: message
        transactionUuid = UUID.randomUUID();
        log.info("Received sessions request with criteria: {}, process's UUID: {}", request, transactionUuid);


        sessions = Criteria.<Session>of(entityManager)
                .root(Session.class)
                .where(boundedToRequestSender(request))
                .where(inRange(request.getFrom(), request.getTo()))
                .join("coach", coachParam(request))
                .join("trainee", traineeParam(request))
                .list();


        response = sessions
                .stream()
                .map(mapper::toSessionDto)
                .toList();


        // todo: message
        log.info("Successfully found sessions with criteria: {}, process's UUID: {}",
                request, transactionUuid);

        return response;
    }


    private BiFunction<CriteriaBuilder, Root<Session>, Predicate> boundedToRequestSender(SessionSearchCriteria request) {
        return (criteriaBuilder, session) -> criteriaBuilder.or(
                criteriaBuilder.equal(session.get("traineeId"), request.getRequestSenderId()),
                criteriaBuilder.equal(session.get("coachId"), request.getRequestSenderId())
        );
    }

    private BiFunction<CriteriaBuilder, Root<Session>, Predicate> inRange(LocalDateTime from, LocalDateTime to) {
        return (cb, session) -> cb.between(
                session.get("date"),
                from,
                to
        );
    }

    private BiFunction<CriteriaBuilder, Join<Session, Coach>, Predicate> coachParam(SessionSearchCriteria request) {
        if (request.getCoachId() != null) {
            return (criteriaBuilder, coach)
                    -> criteriaBuilder.equal(coach.get("id"), request.getCoachId());
        }

        if (request.getCoachUsername() != null) {
            return (criteriaBuilder, coach)
                    -> criteriaBuilder.equal(coach.get("username"), request.getCoachUsername());
        }

        if (request.getCoachFirstName() != null || request.getCoachLastName() != null) {
            return (criteriaBuilder, coach) ->
                    criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    coach.get("firstName"),
                                    request.getCoachFirstName()
                            ),
                            criteriaBuilder.equal(
                                    coach.get("lastName"),
                                    request.getCoachLastName()
                            )
                    );
        }
        return (cb, coach) -> cb.conjunction();
    }

    private BiFunction<CriteriaBuilder, Join<Session, Trainee>, Predicate> traineeParam(SessionSearchCriteria request) {
        if (request.getTraineeId() != null) {
            return (criteriaBuilder, trainee)
                    -> criteriaBuilder.equal(trainee.get("id"), request.getTraineeId());
        }

        if (request.getTraineeUsername() != null) {
            return (criteriaBuilder, trainee)
                    -> criteriaBuilder.equal(trainee.get("username"), request.getTraineeUsername());
        }

        if (request.getTraineeFirstName() != null || request.getTraineeLastName() != null) {
            return (criteriaBuilder, trainee) ->
                    criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    trainee.get("firstName"),
                                    request.getTraineeFirstName()
                            ),
                            criteriaBuilder.equal(
                                    trainee.get("lastName"),
                                    request.getTraineeLastName()
                            )
                    );
        }
        return (cb, trainee) -> cb.conjunction();
    }
}
