package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.*;
import com.fitnesstraining.repository.abstration.SessionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultSessionRepository implements SessionRepository {

    private final EntityManager entityManager;


    public Session create(Session session) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.persist(session);
            transaction.commit();

            log.info("Session with id: {}, coach: {}, trainee: {}, date: {} created",
                    session.getId(), session.getCoach().getUsername(), session.getTrainee().getUsername(), session.getDate());
            return session;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }

}
