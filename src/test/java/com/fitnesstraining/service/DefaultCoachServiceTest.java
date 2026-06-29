package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DefaultCoachServiceTest {

    @Mock
    private CoachRepository coachRepository;

    @InjectMocks
    private DefaultCoachService coachService;

    private Coach coach;

    @BeforeEach
    void setUp() {
        coach = new Coach();
        coach.setId(1L);
        coach.setSpecialization("Java");
    }

    @Test
    void create_ShouldReturnSavedCoach() {
        when(coachRepository.save(coach)).thenReturn(coach);
        Coach result = coachService.create(coach);
        assertNotNull(result);
        assertEquals("Java", result.getSpecialization());
        verify(coachRepository, times(1)).save(coach);
    }


    @Test
    void getById_WhenExists_ShouldReturnCoach() {
        when(coachRepository.findById(1L)).thenReturn(coach);
        Coach result = coachService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedCoach() {
        when(coachRepository.existsById(1L)).thenReturn(true);
        when(coachRepository.save(coach)).thenReturn(coach);
        Coach result = coachService.update(coach);
        assertNotNull(result);
        verify(coachRepository, times(1)).save(coach);
    }

    @Test
    void update_WhenNotExists_ShouldThrowException() {
        when(coachRepository.existsById(1L)).thenReturn(false);
        assertThrows(CoachNotFoundException.class, () -> coachService.update(coach));
        verify(coachRepository, never()).save(any());
    }
}