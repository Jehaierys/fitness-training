package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
import com.fitnesstraining.utils.PasswordGenerator;
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
class TraineeFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private TraineeFacade traineeFacade;

    private User testUser;
    private Trainee testTrainee;
    private final String GENERATED_PASSWORD = "generatedPassword1";

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .username("jane.doe")
                .password(GENERATED_PASSWORD)
                .isActive(true)
                .build();

        testTrainee = Trainee.builder()
                .id(20L)
                .user(testUser) // Updated to use User object
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
    }

    @Test
    void signUp_successfulCreation_returnsTrainee() {
        when(userService.existsByUsername("jane.doe")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
        Trainee result = traineeFacade.signUp(request);

        assertNotNull(result);
        assertEquals(testTrainee.getId(), result.getId());
        assertEquals(testTrainee.getUser().getId(), result.getUser().getId());
        assertEquals(testTrainee.getBirthDate(), result.getBirthDate());
        assertEquals(testTrainee.getAddress(), result.getAddress());

        verify(userService, times(1)).existsByUsername("jane.doe");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(any(User.class));
        verify(traineeService, times(1)).create(any(Trainee.class));
    }

    @Test
    void signUp_usernameCollision_generatesUniqueUsername() {
        when(userService.existsByUsername("jane.doe")).thenReturn(true);
        when(userService.existsByUsername("jane.doe1")).thenReturn(true);
        when(userService.existsByUsername("jane.doe2")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
        Trainee result = traineeFacade.signUp(request);

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername("jane.doe");
        verify(userService, times(1)).existsByUsername("jane.doe1");
        verify(userService, times(1)).existsByUsername("jane.doe2");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals("jane.doe2")));
        verify(traineeService, times(1)).create(any(Trainee.class));
    }

    @Test
    void signUp_userServiceCreateFails_throwsException() {
        when(userService.existsByUsername("jane.doe")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenThrow(new RuntimeException("User creation failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            Trainee result = traineeFacade.signUp(TraineeSignUpRequest.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .address("123 Main St")
                    .build());
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(userService, times(1)).existsByUsername("jane.doe");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(any(User.class));
        verify(traineeService, never()).create(any(Trainee.class)); // Trainee service should not be called
    }

    @Test
    void signUp_traineeServiceCreateFails_throwsExceptionAndUserIsCreated() {
        when(userService.existsByUsername("jane.doe")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenReturn(testUser); // User is successfully created
        when(traineeService.create(any(Trainee.class))).thenThrow(new TraineeNotFoundException("Trainee creation failed"));

        TraineeNotFoundException thrown = assertThrows(TraineeNotFoundException.class, () -> {
            traineeFacade.signUp(
                    TraineeSignUpRequest.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .address("123 Main St")
                    .build());
        });

        assertEquals("Trainee creation failed", thrown.getMessage());
        verify(userService, times(1)).existsByUsername("jane.doe");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(any(User.class)); // User creation should still happen
        verify(traineeService, times(1)).create(any(Trainee.class));
    }

    @Test
    void signUp_emptyFirstAndLastNames_generatesUsernameAndSaves() {
        when(userService.existsByUsername(".")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        Trainee result = traineeFacade.signUp(
                TraineeSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .birthDate(LocalDate.of(2000, 1, 1))
                .address("456 Oak Ave")
                .build()
        );

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername(".");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals(".")));
        verify(traineeService, times(1)).create(any(Trainee.class));
    }

    @Test
    void signUp_emptyFirstAndLastNameWithCollision_generatesUniqueUsername() {
        when(userService.existsByUsername(".")).thenReturn(true);
        when(userService.existsByUsername(".1")).thenReturn(true);
        when(userService.existsByUsername(".2")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        Trainee result = traineeFacade.signUp(
                TraineeSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .birthDate(LocalDate.of(2000, 1, 1))
                .address("456 Oak Ave")
                .build()
        );

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername(".");
        verify(userService, times(1)).existsByUsername(".1");
        verify(userService, times(1)).existsByUsername(".2");
        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals(".2")));
        verify(traineeService, times(1)).create(any(Trainee.class));
    }

    @Test
    void signUp_passwordGeneratorCalled() {
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(passwordGenerator.generate()).thenReturn(GENERATED_PASSWORD);
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        traineeFacade.signUp(
                TraineeSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1985, 1, 1))
                .address("789 Pine Ln")
                .build()
        );

        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getPassword().equals(GENERATED_PASSWORD)));
    }
}