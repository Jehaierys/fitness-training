package com.fitnesstraining.utils;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.domain.User;
import com.fitnesstraining.dto.CoachSignUpRequest;
import com.fitnesstraining.service.abstraction.UserService;
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
class CoachSignUpProcessorTest {

    @Mock
    private UserService userService;

    @Mock
    private SignUpUtils signUpUtils;

    @InjectMocks
    private CoachSignUpProcessor coachSignUpProcessor;

    private CoachSignUpRequest request;
    private User testUser;
    private SessionType testSessionType;
    private Set<SessionType> testSpecializationSet;

    @BeforeEach
    void setUp() {
        testSessionType = SessionType.builder().id(1L).name("Fitness").build();
        testSpecializationSet = Set.of(testSessionType);

        request = CoachSignUpRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(testSpecializationSet)
                .build();

        testUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("generatedPass")
                .isActive(true)
                .build();
    }

    @Test
    void process_SuccessfulSignUp_ReturnsCoach() {
        when(signUpUtils.generatePassword()).thenReturn("generatedPass");
        when(signUpUtils.generateUsername(request.getFirstName(), request.getLastName())).thenReturn("john.doe");
        when(userService.create(any(User.class))).thenReturn(testUser);

        Coach result = coachSignUpProcessor.process(request);

        assertNotNull(result);
        assertEquals(testUser, result.getUser());
        assertEquals(testSpecializationSet, result.getSpecialization());

        verify(signUpUtils, times(1)).generatePassword();
        verify(signUpUtils, times(1)).generateUsername(request.getFirstName(), request.getLastName());
        verify(userService, times(1)).create(argThat(user ->
                user.getFirstName().equals(request.getFirstName()) &&
                user.getLastName().equals(request.getLastName()) &&
                user.getUsername().equals("john.doe") &&
                user.getPassword().equals("generatedPass") &&
                user.isActive()
        ));
    }

    @Test
    void process_UserServiceCreateFails_ThrowsException() {
        when(signUpUtils.generatePassword()).thenReturn("generatedPass");
        when(signUpUtils.generateUsername(request.getFirstName(), request.getLastName())).thenReturn("john.doe");
        when(userService.create(any(User.class))).thenThrow(new RuntimeException("User creation failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            coachSignUpProcessor.process(request);
        });

        assertEquals("User creation failed", thrown.getMessage());
        verify(signUpUtils, times(1)).generatePassword();
        verify(signUpUtils, times(1)).generateUsername(request.getFirstName(), request.getLastName());
        verify(userService, times(1)).create(any(User.class));
    }
}