package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import com.fitnesstraining.service.exception.NotFoundException;
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
    private CoachRepository coachRepository;

    @InjectMocks
    private DefaultCoachService coachService;

    private Coach coach;
    private SessionType testSessionType;
    private Set<SessionType> testSpecializationSet;

    @BeforeEach
    void setUp() {
        testSessionType = SessionType.builder()
                .id(1L)
                .name("Fitness")
                .build();
        testSpecializationSet = Set.of(testSessionType);

        coach = Coach.builder()
                .id(1L)
                .specialization(testSpecializationSet)
                .build();
    }

    @Test
    void create_ShouldReturnSavedCoach() {
        when(coachRepository.save(coach)).thenReturn(coach);
        Coach result = coachService.create(coach);
        assertNotNull(result);
        assertEquals(testSpecializationSet, result.getSpecialization());
        verify(coachRepository, times(1)).save(coach);
    }


    @Test
    void getById_WhenExists_ShouldReturnCoach() {
        when(coachRepository.findById(1L)).thenReturn(Optional.of(coach));
        Coach result = coachService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowNotFoundException() {
        when(coachRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> coachService.getById(99L));
        verify(coachRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedCoach() {
        when(coachRepository.existsById(1L)).thenReturn(true);
        when(coachRepository.save(coach)).thenReturn(coach);
        Coach result = coachService.update(coach);
        assertNotNull(result);
        verify(coachRepository, times(1)).existsById(1L);
        verify(coachRepository, times(1)).save(coach);
    }

    @Test
    void update_WhenNotExists_ShouldThrowCoachNotFoundException() {
        when(coachRepository.existsById(1L)).thenReturn(false);
        assertThrows(CoachNotFoundException.class, () -> coachService.update(coach));
        verify(coachRepository, times(1)).existsById(1L);
        verify(coachRepository, never()).save(any());
    }
}