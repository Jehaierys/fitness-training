package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachProfileUpdateRequest;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import com.fitnesstraining.utils.CoachSignUpProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoachFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private CoachService coachService;

    @Mock
    private CoachSignUpProcessor coachSignUpProcessor;

    @InjectMocks
    private CoachFacade coachFacade;

    private User testUser;
    private Coach testCoach;
    private SessionType testSessionType;
    private Set<SessionType> testSpecializationSet;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("randompass")
                .isActive(true)
                .build();

        testSessionType = SessionType.builder()
                .id(1L)
                .name("Fitness")
                .build();
        testSpecializationSet = Set.of(testSessionType);

        testCoach = Coach.builder()
                .id(10L)
                .user(testUser)
                .specialization(testSpecializationSet)
                .build();
    }

    @Test
    void signUp_successfulCreation_returnsCoach() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenReturn(testCoach);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        assertEquals(testCoach.getId(), result.getId());
        assertEquals(testCoach.getUser().getId(), result.getUser().getId());
        assertEquals(testSpecializationSet, result.getSpecialization());

        verify(coachSignUpProcessor, times(1)).process(request);
        verify(coachService, times(1)).create(testCoach);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_usernameCollision_generatesUniqueUsername() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenReturn(testCoach);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(coachSignUpProcessor, times(1)).process(request);
        verify(coachService, times(1)).create(testCoach);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_userServiceCreateFails_throwsException() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenThrow(new RuntimeException("User creation failed"));
        verifyNoInteractions(coachService);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            coachFacade.signUp(request);
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(coachSignUpProcessor, times(1)).process(request);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_coachServiceCreateFails_throwsExceptionAndUserIsCreated() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenReturn(testCoach);
        when(coachService.create(any(Coach.class))).thenThrow(new CoachNotFoundException("Coach creation failed"));

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        CoachNotFoundException thrown = assertThrows(CoachNotFoundException.class, () -> {
            coachFacade.signUp(request);
        });

        assertEquals("Coach creation failed", thrown.getMessage());
        verify(coachSignUpProcessor, times(1)).process(request);
        verify(coachService, times(1)).create(testCoach);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_emptyFirstAndLastNames_generatesUsernameAndSaves() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenReturn(testCoach);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(coachSignUpProcessor, times(1)).process(request);
        verify(coachService, times(1)).create(testCoach);
        verifyNoInteractions(userService);
    }

    @Test
    void signUp_emptyFirstAndLastNameWithCollision_generatesUniqueUsername() {
        when(coachSignUpProcessor.process(any(CoachSignUpRequest.class))).thenReturn(testCoach);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(coachSignUpProcessor, times(1)).process(request);
        verify(coachService, times(1)).create(testCoach);
        verifyNoInteractions(userService);
    }

    @Test
    void updateProfile_SuccessfulUpdate_ReturnsUpdatedCoach() {
        User updatedUser = User.builder()
                .id(1L)
                .firstName("UpdatedJohn")
                .lastName("UpdatedDoe")
                .username("john.doe")
                .password("randompass")
                .isActive(true)
                .build();
        SessionType newSessionType = SessionType.builder().id(2L).name("Yoga").build();
        Set<SessionType> newSpecializationSet = Set.of(newSessionType);
        Coach updatedCoach = Coach.builder()
                .id(10L)
                .user(updatedUser)
                .specialization(newSpecializationSet)
                .build();

        when(coachService.getById(testCoach.getId())).thenReturn(testCoach);
        when(userService.update(any(User.class))).thenReturn(updatedUser);
        when(coachService.update(any(Coach.class))).thenReturn(updatedCoach);

        CoachProfileUpdateRequest request = CoachProfileUpdateRequest.builder()
                .id(testCoach.getId())
                .firstName("UpdatedJohn")
                .lastName("UpdatedDoe")
                .specialization(newSpecializationSet)
                .build();

        Coach result = coachFacade.updateProfile(request);

        assertNotNull(result);
        assertEquals(updatedCoach.getId(), result.getId());
        assertEquals(updatedUser.getFirstName(), result.getUser().getFirstName());
        assertEquals(updatedUser.getLastName(), result.getUser().getLastName());
        assertEquals(newSpecializationSet, result.getSpecialization());

        verify(coachService, times(1)).getById(testCoach.getId());
        verify(userService, times(1)).update(argThat(user ->
                user.getFirstName().equals("UpdatedJohn") && user.getLastName().equals("UpdatedDoe")));
        verify(coachService, times(1)).update(argThat(coach ->
                coach.getSpecialization().equals(newSpecializationSet)));
    }

    @Test
    void updateProfile_CoachNotFound_ThrowsCoachNotFoundException() {
        Long nonExistentCoachId = 99L;
        when(coachService.getById(nonExistentCoachId)).thenThrow(new CoachNotFoundException("Coach not found"));

        CoachProfileUpdateRequest request = CoachProfileUpdateRequest.builder()
                .id(nonExistentCoachId)
                .firstName("Any")
                .lastName("Name")
                .specialization(Set.of())
                .build();

        assertThrows(CoachNotFoundException.class, () -> coachFacade.updateProfile(request));

        verify(coachService, times(1)).getById(nonExistentCoachId);
        verify(userService, never()).update(any(User.class));
        verify(coachService, never()).update(any(Coach.class));
    }
}