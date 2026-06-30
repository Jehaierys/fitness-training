package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import com.fitnesstraining.service.exception.NotFoundException; // Import NotFoundException
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
        when(coachRepository.findById(1L)).thenReturn(Optional.of(coach)); // Corrected mocking
        Coach result = coachService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(coachRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowNotFoundException() { // New test case
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
    void update_WhenNotExists_ShouldThrowCoachNotFoundException() { // Renamed for clarity
        when(coachRepository.existsById(1L)).thenReturn(false);
        assertThrows(CoachNotFoundException.class, () -> coachService.update(coach));
        verify(coachRepository, times(1)).existsById(1L);
        verify(coachRepository, never()).save(any());
    }
}