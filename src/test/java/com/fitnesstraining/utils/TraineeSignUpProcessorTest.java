package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeSignUpProcessorTest {

    @Mock
    private UserService userService;

    @Mock
    private SignUpUtils signUpUtils;

    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeSignUpProcessor traineeSignUpProcessor;

    private TraineeSignUpRequest request;
    private User testUser;
    private Trainee testTrainee;

    @BeforeEach
    void setUp() {
        request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();

        testUser = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .username("jane.doe")
                .password("generatedPass")
                .isActive(true)
                .build();

        testTrainee = Trainee.builder()
                .id(20L)
                .user(testUser)
                .birthDate(request.getBirthDate())
                .address(request.getAddress())
                .build();
    }

    @Test
    void process_SuccessfulSignUp_ReturnsTrainee() {
        when(signUpUtils.generatePassword()).thenReturn("generatedPass");
        when(signUpUtils.generateUsername(request.getFirstName(), request.getLastName())).thenReturn("jane.doe");
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        Trainee result = traineeSignUpProcessor.process(request);

        assertNotNull(result);
        assertEquals(testTrainee, result);

        verify(signUpUtils, times(1)).generatePassword();
        verify(signUpUtils, times(1)).generateUsername(request.getFirstName(), request.getLastName());
        verify(userService, times(1)).create(argThat(user ->
                user.getFirstName().equals(request.getFirstName()) &&
                user.getLastName().equals(request.getLastName()) &&
                user.getUsername().equals("jane.doe") &&
                user.getPassword().equals("generatedPass") &&
                user.isActive()
        ));
        verify(traineeService, times(1)).create(argThat(trainee ->
                trainee.getUser().equals(testUser) &&
                trainee.getBirthDate().equals(request.getBirthDate()) &&
                trainee.getAddress().equals(request.getAddress())
        ));
    }

    @Test
    void process_UserServiceCreateFails_ThrowsException() {
        when(signUpUtils.generatePassword()).thenReturn("generatedPass");
        when(signUpUtils.generateUsername(request.getFirstName(), request.getLastName())).thenReturn("jane.doe");
        when(userService.create(any(User.class))).thenThrow(new RuntimeException("User creation failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            traineeSignUpProcessor.process(request);
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(signUpUtils, times(1)).generatePassword();
        verify(signUpUtils, times(1)).generateUsername(request.getFirstName(), request.getLastName());
        verify(userService, times(1)).create(any(User.class));
        verify(traineeService, never()).create(any(Trainee.class));
    }

    @Test
    void process_TraineeServiceCreateFails_ThrowsException() {
        when(signUpUtils.generatePassword()).thenReturn("generatedPass");
        when(signUpUtils.generateUsername(request.getFirstName(), request.getLastName())).thenReturn("jane.doe");
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(traineeService.create(any(Trainee.class))).thenThrow(new RuntimeException("Trainee creation failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            traineeSignUpProcessor.process(request);
        });

        assertEquals("Trainee creation failed", thrown.getMessage());
        verify(signUpUtils, times(1)).generatePassword();
        verify(signUpUtils, times(1)).generateUsername(request.getFirstName(), request.getLastName());
        verify(userService, times(1)).create(any(User.class));
        verify(traineeService, times(1)).create(any(Trainee.class));
    }
}