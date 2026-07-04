package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachProfileUpdateRequest;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import com.fitnesstraining.utils.PasswordGenerator;
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
    private PasswordGenerator passwordGenerator;

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
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenReturn(testUser);
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

        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).create(any(User.class));
        verify(coachService, times(1)).create(any(Coach.class));
    }

    @Test
    void signUp_usernameCollision_generatesUniqueUsername() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(true);
        when(userService.existsByUsername("john.doe1")).thenReturn(true);
        when(userService.existsByUsername("john.doe2")).thenReturn(false);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).existsByUsername("john.doe1");
        verify(userService, times(1)).existsByUsername("john.doe2");
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals("john.doe2")));
        verify(coachService, times(1)).create(any(Coach.class));
    }

    @Test
    void signUp_userServiceCreateFails_throwsException() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenThrow(new RuntimeException("User creation failed"));

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            coachFacade.signUp(request);
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).create(any(User.class));
        verify(coachService, never()).create(any(Coach.class));
    }

    @Test
    void signUp_coachServiceCreateFails_throwsExceptionAndUserIsCreated() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenReturn(testUser);
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
        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).create(any(User.class));
        verify(coachService, times(1)).create(any(Coach.class));
    }

    @Test
    void signUp_emptyFirstAndLastNames_generatesUsernameAndSaves() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername(".")).thenReturn(false);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername(".");
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals(".")));
        verify(coachService, times(1)).create(any(Coach.class));
    }

    @Test
    void signUp_emptyFirstAndLastNameWithCollision_generatesUniqueUsername() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername(".")).thenReturn(true);
        when(userService.existsByUsername(".1")).thenReturn(true);
        when(userService.existsByUsername(".2")).thenReturn(false);
        when(userService.create(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("")
                .lastName("")
                .specialization(testSpecializationSet)
                .build();
        Coach result = coachFacade.signUp(request);

        assertNotNull(result);
        verify(userService, times(1)).existsByUsername(".");
        verify(userService, times(1)).existsByUsername(".1");
        verify(userService, times(1)).existsByUsername(".2");
        verify(userService, times(1)).create(argThat(user -> user.getUsername().equals(".2")));
        verify(coachService, times(1)).create(any(Coach.class));
    }

    @Test
    void signUp_usesPasswordGenerator() {
        when(passwordGenerator.generate()).thenReturn("securePass123");
        when(userService.existsByUsername("jane.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        CoachSignUpRequest request = CoachSignUpRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();
        coachFacade.signUp(request);

        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getPassword().equals("securePass123")));
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