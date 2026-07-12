package com.fitnesstraining.repository;

import com.fitnesstraining.domain.dto.session.SessionSearchCriteria;
import com.fitnesstraining.domain.entity.*;
import com.fitnesstraining.repository.abstration.SessionRepository;
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
public class DefaultSessionRepository implements SessionRepository {

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
