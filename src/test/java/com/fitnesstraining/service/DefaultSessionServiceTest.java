package com.fitnesstraining.service;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.service.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Session result = sessionService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowNotFoundException() {
        when(sessionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.getById(99L));
        verify(sessionRepository, times(1)).findById(99L);
    }
}