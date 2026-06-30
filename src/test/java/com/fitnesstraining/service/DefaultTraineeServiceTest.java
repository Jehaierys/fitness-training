package com.fitnesstraining.service;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.repository.TraineeRepository;
import com.fitnesstraining.service.exception.NotFoundException;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
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
class DefaultTraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private DefaultTraineeService traineeService;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setAddress("Test Address");
    }

    @Test
    void create_ShouldReturnSavedTrainee() {
        when(traineeRepository.save(trainee)).thenReturn(trainee);
        Trainee result = traineeService.create(trainee);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void getById_WhenExists_ShouldReturnTrainee() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        Trainee result = traineeService.getById(1L);
        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowNotFoundException() {
        when(traineeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> traineeService.getById(99L));
        verify(traineeRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedTrainee() {
        when(traineeRepository.existsById(1L)).thenReturn(true);
        when(traineeRepository.save(trainee)).thenReturn(trainee);

        Trainee result = traineeService.update(trainee);

        assertNotNull(result);
        verify(traineeRepository, times(1)).existsById(1L);
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void update_WhenNotExists_ShouldThrowTraineeNotFoundException() {
        when(traineeRepository.existsById(1L)).thenReturn(false);
        assertThrows(TraineeNotFoundException.class, () -> traineeService.update(trainee));
        verify(traineeRepository, times(1)).existsById(1L);
        verify(traineeRepository, never()).save(any());
    }


    @Test
    void delete_WhenExists_ShouldCallRepositoryDelete() {
        long traineeId = 1L;
        when(traineeRepository.existsById(traineeId)).thenReturn(true);

        traineeService.delete(traineeId);

        verify(traineeRepository, times(1)).existsById(traineeId);
        verify(traineeRepository, times(1)).deleteById(traineeId);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowTraineeNotFoundException() {
        long traineeId = 99L;
        when(traineeRepository.existsById(traineeId)).thenReturn(false);

        assertThrows(TraineeNotFoundException.class, () -> traineeService.delete(traineeId));

        verify(traineeRepository, times(1)).existsById(traineeId);
        verify(traineeRepository, never()).deleteById(anyLong());
    }
}