package com.fitnesstraining.service;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.TraineeRepository;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .username("jane.doe")
                .password("password123")
                .isActive(true)
                .build();

        trainee = Trainee.builder()
                .id(1L)
                .user(testUser)
                .address("Test Address")
                .birthDate(LocalDate.of(1990, 1, 1))
                .build();
    }

    @Test
    void create_ShouldReturnSavedTrainee() {
        when(traineeRepository.create(trainee)).thenReturn(trainee);
        Trainee result = traineeService.create(trainee);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(testUser, result.getUser());
        verify(traineeRepository, times(1)).create(trainee);
    }

    @Test
    void getById_WhenExists_ShouldReturnTrainee() {
        when(traineeRepository.findById(1L)).thenReturn(Optional.of(trainee));
        Trainee result = traineeService.getById(1L);
        assertNotNull(result);
        assertEquals("Test Address", result.getAddress());
        assertEquals(testUser, result.getUser());
        verify(traineeRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowTraineeNotFoundException() {
        when(traineeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TraineeNotFoundException.class, () -> traineeService.getById(99L));
        verify(traineeRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedTrainee() {
        when(traineeRepository.update(trainee)).thenReturn(trainee);
        Trainee result = traineeService.update(trainee);
        assertNotNull(result);
        assertEquals(testUser, result.getUser());
        verify(traineeRepository, times(1)).update(trainee);
    }

    @Test
    void update_WhenNotExists_ShouldThrowIllegalArgumentException() {
        when(traineeRepository.update(trainee)).thenThrow(new IllegalArgumentException("Trainee must have an ID and exist in the database to be updated."));
        assertThrows(IllegalArgumentException.class, () -> traineeService.update(trainee));
        verify(traineeRepository, times(1)).update(trainee);
    }


    @Test
    void delete_WhenExists_ShouldCallRepositoryDeleteById() {
        long traineeId = 1L;
        when(traineeRepository.existsById(traineeId)).thenReturn(true);
        doNothing().when(traineeRepository).deleteById(traineeId);

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
        verify(traineeRepository, never()).delete(any(Trainee.class));
        verify(traineeRepository, never()).deleteById(anyLong());
    }
}