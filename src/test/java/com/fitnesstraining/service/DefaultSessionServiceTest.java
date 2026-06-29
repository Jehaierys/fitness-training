package com.fitnesstraining.service;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultSessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @InjectMocks
    private DefaultSessionService sessionService;

    private Session session;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);
        session.setName("Intro Session");
    }

    @Test
    void create_ShouldReturnSavedSession() {
        when(sessionRepository.save(session)).thenReturn(session);
        Session result = sessionService.create(session);
        assertNotNull(result);
        assertEquals("Intro Session", result.getName());
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    void getById_WhenExists_ShouldReturnSession() {
        when(sessionRepository.findById(1L)).thenReturn(session);
        Session result = sessionService.getById(1L);
        assertTrue(result != null);
        assertEquals(1L, result.getId());
    }
}