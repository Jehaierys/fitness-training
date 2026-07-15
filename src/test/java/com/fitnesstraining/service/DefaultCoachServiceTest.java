package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.DefaultCoachRepository;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DefaultCoachServiceTest {

    @Mock
    private DefaultCoachRepository coachRepository;

    @InjectMocks
    private DefaultCoachService coachService;

    private Coach coach;
    private SessionType testSessionType;
    private Set<SessionType> testSpecializationSet;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("password123")
                .isActive(true)
                .build();

        testSessionType = SessionType.builder()
                .id(1L)
                .name("Fitness")
                .build();
        testSpecializationSet = Set.of(testSessionType);

        coach = Coach.builder()
                .id(1L)
                .user(testUser)
                .specialization(testSpecializationSet)
                .build();
    }

    @Test
    void create_ShouldReturnSavedCoach() {
        when(coachRepository.create(coach)).thenReturn(coach);
        Coach result = coachService.create(coach);
        assertNotNull(result);
        assertEquals(testSpecializationSet, result.getSpecialization());
        assertEquals(testUser, result.getUser());
        verify(coachRepository, times(1)).create(coach);
    }


    @Test
    void getById_WhenExists_ShouldReturnCoach() {
        when(coachRepository.findById(1L)).thenReturn(Optional.of(coach));
        Coach result = coachService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testUser, result.getUser());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowCoachNotFoundException() {
        when(coachRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CoachNotFoundException.class, () -> coachService.getById(99L));
        verify(coachRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedCoach() {
        when(coachRepository.update(coach)).thenReturn(coach);
        Coach result = coachService.update(coach);
        assertNotNull(result);
        assertEquals(testUser, result.getUser());
        verify(coachRepository, times(1)).update(coach);
    }

    @Test
    void update_WhenNotExists_ShouldThrowIllegalArgumentException() {
        when(coachRepository.update(coach)).thenThrow(new IllegalArgumentException("Coach must have an ID and exist in the database to be updated."));
        assertThrows(IllegalArgumentException.class, () -> coachService.update(coach));
        verify(coachRepository, times(1)).update(coach);
    }
}