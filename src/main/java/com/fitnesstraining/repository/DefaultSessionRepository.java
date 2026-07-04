package com.fitnesstraining.repository;

import com.fitnesstraining.domain.*;
import com.fitnesstraining.dto.SessionSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultSessionRepository {

    private final EntityManager entityManager;

    public Session create(Session session) {
        entityManager.persist(session);
        log.info("Session with id: {} created", session.getId());
        return session;
    }

    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }

    public List<Session> searchSessions(SessionSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Session> cq = cb.createQuery(Session.class);
        Root<Session> session = cq.from(Session.class);

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getTraineeUsername() != null && !criteria.getTraineeUsername().isEmpty()) {
            Join<Session, Trainee> traineeJoin = session.join("trainee");
            Join<Trainee, User> traineeUserJoin = traineeJoin.join("user");
            predicates.add(cb.equal(traineeUserJoin.get("username"), criteria.getTraineeUsername()));
        }

        // Added predicate for coachUsername
        if (criteria.getCoachUsername() != null && !criteria.getCoachUsername().isEmpty()) {
            Join<Session, Coach> coachJoin = session.join("coach");
            Join<Coach, User> coachUserJoin = coachJoin.join("user");
            predicates.add(cb.equal(coachUserJoin.get("username"), criteria.getCoachUsername()));
        }

        if (criteria.getFromDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(session.get("date"), criteria.getFromDate().atStartOfDay()));
        }
        if (criteria.getToDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(session.get("date"), criteria.getToDate().atTime(23, 59, 59)));
        }

        if (criteria.getCoachFirstName() != null && !criteria.getCoachFirstName().isEmpty()) {
            Join<Session, Coach> coachJoin = session.join("coach");
            Join<Coach, User> coachUserJoin = coachJoin.join("user");
            predicates.add(cb.equal(coachUserJoin.get("firstName"), criteria.getCoachFirstName()));
        }
        if (criteria.getCoachLastName() != null && !criteria.getCoachLastName().isEmpty()) {
            Join<Session, Coach> coachJoin = session.join("coach");
            Join<Coach, User> coachUserJoin = coachJoin.join("user");
            predicates.add(cb.equal(coachUserJoin.get("lastName"), criteria.getCoachLastName()));
        }

        if (criteria.getSessionType() != null && !criteria.getSessionType().isEmpty()) {
            Join<Session, SessionType> sessionTypeJoin = session.join("sessionType");
            predicates.add(cb.equal(sessionTypeJoin.get("name"), criteria.getSessionType()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq).getResultList();
    }
}
