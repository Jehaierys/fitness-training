package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Test
    void save_ShouldStoreSessionWithoutAffectingOthers() {
        Session session = new Session();
        session.setName("Cardio Intensive");
        Session saved = sessionRepository.save(session);
        assertNotNull(saved.getId());
        Session found = sessionRepository.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Cardio Intensive", found.getName());
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmptyOptional() {
        Session found = sessionRepository.findById(9999L);
        assertNull(found);
    }
}