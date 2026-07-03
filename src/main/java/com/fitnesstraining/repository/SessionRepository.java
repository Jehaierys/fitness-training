package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Session;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Slf4j
@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final EntityManager entityManager;

    public Session create(Session session) {
        entityManager.persist(session);
        log.info("Session with id: {} created", session.getId());
        return session;
    }

    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Session.class, id));
    }
}