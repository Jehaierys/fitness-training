package com.fitnesstraining.utils;

import com.fitnesstraining.service.abstraction.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpUtilsTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SignUpUtils signUpUtils;

    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @BeforeEach
    void setUp() {
        reset(userService);
    }

    @Test
    void generatePassword_ShouldReturnTenCharString() {
        String password = signUpUtils.generatePassword();
        assertNotNull(password);
        assertEquals(10, password.length());
        assertTrue(password.matches("[" + CHARACTERS + "]{10}"));
    }

    @Test
    void generatePassword_ShouldReturnDifferentPasswords() {
        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            passwords.add(signUpUtils.generatePassword());
        }
        assertEquals(100, passwords.size(), "Should generate mostly unique passwords");
    }

    @Test
    void generateUsername_ShouldReturnBaseUsernameIfUnique() {
        String firstName = "John";
        String lastName = "Doe";
        String expectedUsername = "john.doe";

        when(userService.existsByUsername(expectedUsername)).thenReturn(false);

        String username = signUpUtils.generateUsername(firstName, lastName);

        assertEquals(expectedUsername, username);
        verify(userService, times(1)).existsByUsername(expectedUsername);
    }

    @Test
    void generateUsername_ShouldReturnUsernameWithSuffixIfCollision() {
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = "john.doe";
        String expectedUsername1 = "john.doe1";
        String expectedUsername2 = "john.doe2";

        when(userService.existsByUsername(baseUsername)).thenReturn(true);
        when(userService.existsByUsername(expectedUsername1)).thenReturn(true);
        when(userService.existsByUsername(expectedUsername2)).thenReturn(false);

        String username = signUpUtils.generateUsername(firstName, lastName);

        assertEquals(expectedUsername2, username);
        verify(userService, times(1)).existsByUsername(baseUsername);
        verify(userService, times(1)).existsByUsername(expectedUsername1);
        verify(userService, times(1)).existsByUsername(expectedUsername2);
    }

    @Test
    void generateUsername_ShouldHandleEmptyNames() {
        String firstName = "";
        String lastName = "";
        String baseUsername = ".";

        when(userService.existsByUsername(baseUsername)).thenReturn(false);

        String username = signUpUtils.generateUsername(firstName, lastName);

        assertEquals(baseUsername, username);
        verify(userService, times(1)).existsByUsername(baseUsername);
    }

    @Test
    void generateUsername_ShouldHandleEmptyNamesWithCollision() {
        String firstName = "";
        String lastName = "";
        String baseUsername = ".";
        String expectedUsername1 = ".1";
        String expectedUsername2 = ".2";

        when(userService.existsByUsername(baseUsername)).thenReturn(true);
        when(userService.existsByUsername(expectedUsername1)).thenReturn(true);
        when(userService.existsByUsername(expectedUsername2)).thenReturn(false);

        String username = signUpUtils.generateUsername(firstName, lastName);

        assertEquals(expectedUsername2, username);
        verify(userService, times(1)).existsByUsername(baseUsername);
        verify(userService, times(1)).existsByUsername(expectedUsername1);
        verify(userService, times(1)).existsByUsername(expectedUsername2);
    }
}