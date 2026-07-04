package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.TraineeProfileUpdateRequest;
import com.fitnesstraining.dto.TraineeSignUpRequest;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
import com.fitnesstraining.utils.TraineeSignUpProcessor;
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
    private TraineeSignUpProcessor traineeSignUpProcessor;

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
                .user(testUser)
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
    }

    @Test
    void signUp_successfulCreation_returnsTrainee() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenReturn(testTrainee);
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

        verify(traineeSignUpProcessor, times(1)).process(request);
        verify(traineeService, times(1)).create(testTrainee);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_usernameCollision_generatesUniqueUsername() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenReturn(testTrainee);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
        Trainee result = traineeFacade.signUp(request);

        assertNotNull(result);
        verify(traineeSignUpProcessor, times(1)).process(request);
        verify(traineeService, times(1)).create(testTrainee);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_userServiceCreateFails_throwsException() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenThrow(new RuntimeException("User creation failed"));
        verifyNoInteractions(traineeService);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            traineeFacade.signUp(request);
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(traineeSignUpProcessor, times(1)).process(request);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_traineeServiceCreateFails_throwsExceptionAndUserIsCreated() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenReturn(testTrainee);
        when(traineeService.create(any(Trainee.class))).thenThrow(new TraineeNotFoundException("Trainee creation failed"));

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .birthDate(LocalDate.of(1990, 5, 15))
                .address("123 Main St")
                .build();
        TraineeNotFoundException thrown = assertThrows(TraineeNotFoundException.class, () -> {
            traineeFacade.signUp(request);
        });

        assertEquals("Trainee creation failed", thrown.getMessage());
        verify(traineeSignUpProcessor, times(1)).process(request);
        verify(traineeService, times(1)).create(testTrainee);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_emptyFirstAndLastNames_generatesUsernameAndSaves() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenReturn(testTrainee);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .birthDate(LocalDate.of(2000, 1, 1))
                .address("456 Oak Ave")
                .build();
        Trainee result = traineeFacade.signUp(request);

        assertNotNull(result);
        verify(traineeSignUpProcessor, times(1)).process(request);
        verify(traineeService, times(1)).create(testTrainee);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_emptyFirstAndLastNameWithCollision_generatesUniqueUsername() {
        when(traineeSignUpProcessor.process(any(TraineeSignUpRequest.class))).thenReturn(testTrainee);
        when(traineeService.create(any(Trainee.class))).thenReturn(testTrainee);

        TraineeSignUpRequest request = TraineeSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .birthDate(LocalDate.of(2000, 1, 1))
                .address("456 Oak Ave")
                .build();
        Trainee result = traineeFacade.signUp(request);

        assertNotNull(result);
        verify(traineeSignUpProcessor, times(1)).process(request);
        verify(traineeService, times(1)).create(testTrainee);
        verifyNoInteractions(userService);
    }

    @Test
    void updateProfile_SuccessfulUpdate_ReturnsUpdatedTrainee() {
        User updatedUser = User.builder()
                .id(1L)
                .firstName("UpdatedJane")
                .lastName("UpdatedDoe")
                .username("jane.doe")
                .password(GENERATED_PASSWORD)
                .isActive(true)
                .build();
        Trainee updatedTrainee = Trainee.builder()
                .id(20L)
                .user(updatedUser)
                .birthDate(LocalDate.of(1995, 10, 20))
                .address("456 New St")
                .build();

        when(traineeService.getById(testTrainee.getId())).thenReturn(testTrainee);
        when(userService.update(any(User.class))).thenReturn(updatedUser);
        when(traineeService.update(any(Trainee.class))).thenReturn(updatedTrainee);

        TraineeProfileUpdateRequest request = TraineeProfileUpdateRequest.builder()
                .id(testTrainee.getId())
                .firstName("UpdatedJane")
                .lastName("UpdatedDoe")
                .birthDate(LocalDate.of(1995, 10, 20))
                .address("456 New St")
                .build();

        Trainee result = traineeFacade.updateProfile(request);

        assertNotNull(result);
        assertEquals(updatedTrainee.getId(), result.getId());
        assertEquals(updatedUser.getFirstName(), result.getUser().getFirstName());
        assertEquals(updatedUser.getLastName(), result.getUser().getLastName());
        assertEquals(updatedTrainee.getBirthDate(), result.getBirthDate());
        assertEquals(updatedTrainee.getAddress(), result.getAddress());

        verify(traineeService, times(1)).getById(testTrainee.getId());
        verify(userService, times(1)).update(argThat(user ->
                user.getFirstName().equals("UpdatedJane") && user.getLastName().equals("UpdatedDoe")));
        verify(traineeService, times(1)).update(argThat(trainee ->
                trainee.getBirthDate().equals(LocalDate.of(1995, 10, 20)) && trainee.getAddress().equals("456 New St")));
    }

    @Test
    void updateProfile_TraineeNotFound_ThrowsTraineeNotFoundException() {
        Long nonExistentTraineeId = 99L;
        when(traineeService.getById(nonExistentTraineeId)).thenThrow(new TraineeNotFoundException("Trainee not found"));

        TraineeProfileUpdateRequest request = TraineeProfileUpdateRequest.builder()
                .id(nonExistentTraineeId)
                .firstName("Any")
                .lastName("Name")
                .birthDate(LocalDate.now())
                .address("Any Address")
                .build();

        assertThrows(TraineeNotFoundException.class, () -> traineeFacade.updateProfile(request));

        verify(traineeService, times(1)).getById(nonExistentTraineeId);
        verify(userService, never()).update(any(User.class));
        verify(traineeService, never()).update(any(Trainee.class));
    }
}