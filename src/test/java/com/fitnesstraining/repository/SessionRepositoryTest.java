package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.testUtils.entity.Sessions;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@DataJpaTest(showSql = false)
@Disabled
class SessionRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private SessionRepository sessionRepository;

    private Session session;

    @Test
    void saveSession() {
        session = Sessions.fullAndValid();

        sessionRepository.create(session);

        entityManager.flush();

        assertNotNull(session.getId());
    }
}