package com.fitnesstraining.service.facade;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.User;
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

        testCoach = Coach.builder()
                .id(10L)
                .userId(1L)
                .specialization("Fitness")
                .build();
    }

    @Test
    void signUp_successfulCreation_returnsCoach() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenReturn(testUser);
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        Coach result = coachFacade.signUp("John", "Doe", "Fitness");

        assertNotNull(result);
        assertEquals(testCoach.getId(), result.getId());
        assertEquals(testCoach.getUserId(), result.getUserId());
        assertEquals(testCoach.getSpecialization(), result.getSpecialization());

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
            user.setId(1L); // Simulate ID assignment
            return user;
        });
        when(coachService.create(any(Coach.class))).thenReturn(testCoach);

        Coach result = coachFacade.signUp("John", "Doe", "Fitness");

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

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            coachFacade.signUp("John", "Doe", "Fitness");
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).create(any(User.class));
        verify(coachService, never()).create(any(Coach.class)); // Coach service should not be called
    }

    @Test
    void signUp_coachServiceCreateFails_throwsExceptionAndUserIsCreated() {
        when(passwordGenerator.generate()).thenReturn("randompass");
        when(userService.existsByUsername("john.doe")).thenReturn(false);
        when(userService.create(any(User.class))).thenReturn(testUser); // User is successfully created
        when(coachService.create(any(Coach.class))).thenThrow(new CoachNotFoundException("Coach creation failed"));

        CoachNotFoundException thrown = assertThrows(CoachNotFoundException.class, () -> {
            coachFacade.signUp("John", "Doe", "Fitness");
        });

        assertEquals("Coach creation failed", thrown.getMessage());
        verify(userService, times(1)).existsByUsername("john.doe");
        verify(userService, times(1)).create(any(User.class)); // User creation should still happen
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

        Coach result = coachFacade.signUp("", "", "Yoga");

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

        Coach result = coachFacade.signUp("", "", "Yoga");

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

        coachFacade.signUp("Jane", "Doe", "Pilates");

        verify(passwordGenerator, times(1)).generate();
        verify(userService, times(1)).create(argThat(user -> user.getPassword().equals("securePass123")));
    }
}