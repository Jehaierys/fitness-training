package com.fitnesstraining.logic.processor;

import com.fitnesstraining.domain.dto.session.SessionSearchCriteria;
import com.fitnesstraining.domain.dto.session.SessionDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.logic.mapper.SessionMapper;
import com.fitnesstraining.repository.dsl.Criteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @PersistenceContext
    private final EntityManager entityManager;
    private final SessionMapper mapper;

    private SessionSearchCriteria request;
    private UUID transactionUuid;
    private List<Session> sessions;
    private List<SessionDto> response;


    public synchronized List<SessionDto> searchByCriteria(SessionSearchCriteria request) {
        this.request = request;
        initialLog();

        findByCriteria();
        mapToDto();

        finalLog();
        return response;
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Received sessions request with criteria: {}, process's UUID: {}", request, transactionUuid);
    }

    private void findByCriteria() {
        sessions = Criteria.<Session>of(entityManager)
                .root(Session.class)
                .where(boundedToRequestSender())
                .where(inRange(request.getFrom(), request.getTo()))
                .join("coach", coachParam())
                .join("trainee", traineeParam())
                .list();
    }

    private BiFunction<CriteriaBuilder, Root<Session>, Predicate> boundedToRequestSender() {
        return (criteriaBuilder, session) -> criteriaBuilder.or(
                criteriaBuilder.equal(session.get("traineeId"), request.getRequestSenderId()),
                criteriaBuilder.equal(session.get("coachId"), request.getRequestSenderId())
        );
    }


    private BiFunction<CriteriaBuilder, Root<Session>, Predicate> inRange(LocalDateTime from, LocalDateTime to) {
        return (cb, session) -> cb.between(
                session.get("date"),
                request.getFrom(),
                request.getTo()
        );
    }

    private BiFunction<CriteriaBuilder, Join<Session, Coach>, Predicate> coachParam() {
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

    private BiFunction<CriteriaBuilder, Join<Session, Trainee>, Predicate> traineeParam() {
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

    private void mapToDto() {
        response = sessions
                .stream()
                .map(mapper::toSessionDto)
                .toList();
    }

    private void finalLog() {
        log.info("Successfully found sessions with criteria: {}, process's UUID: {}", request, transactionUuid);
    }
}
