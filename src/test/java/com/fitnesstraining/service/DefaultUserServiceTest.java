package com.fitnesstraining.service;

import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.UserRepository;
import com.fitnesstraining.service.exception.UserNotFoundException;
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
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    private User user;
    private final String TEST_USERNAME = "john.doe";
    private final String TEST_PASSWORD = "password123";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .isActive(true)
                .build();
    }

    @Test
    void create_ShouldReturnSavedUser() {
        when(userRepository.create(user)).thenReturn(user);
        User result = userService.create(user);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(TEST_USERNAME, result.getUsername());
        verify(userRepository, times(1)).create(user);
    }

    @Test
    void getById_WhenExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User result = userService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedUser() {
        when(userRepository.update(user)).thenReturn(user);
        User result = userService.update(user);
        assertNotNull(result);
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void update_WhenNotExists_ShouldThrowIllegalArgumentException() {
        when(userRepository.update(user)).thenThrow(new IllegalArgumentException("User must have an ID and exist in the database to be updated."));
        assertThrows(IllegalArgumentException.class, () -> userService.update(user));
        verify(userRepository, times(1)).update(user);
    }

    @Test
    void delete_WhenExists_ShouldCallRepositoryDeleteById() {
        long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);
        userService.delete(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowUserNotFoundException() {
        long userId = 99L;
        doThrow(new UserNotFoundException("User not found with id: " + userId)).when(userRepository).deleteById(userId);
        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void existsByUsername_WhenExists_ShouldReturnTrue() {
        when(userRepository.existByUsername(TEST_USERNAME)).thenReturn(true);
        assertTrue(userService.existsByUsername(TEST_USERNAME));
        verify(userRepository, times(1)).existByUsername(TEST_USERNAME);
    }

    @Test
    void existsByUsername_WhenNotExists_ShouldReturnFalse() {
        when(userRepository.existByUsername("nonexistent")).thenReturn(false);
        assertFalse(userService.existsByUsername("nonexistent"));
        verify(userRepository, times(1)).existByUsername("nonexistent");
    }

    @Test
    void setActive_WhenUserExistsAndIsActiveIsTrue_ShouldSetUserActive() {
        User activeUser = User.builder().id(1L).isActive(false).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(activeUser));
        when(userRepository.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.setActive(1L, true);

        assertTrue(activeUser.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).update(activeUser);
    }

    @Test
    void setActive_WhenUserExistsAndIsActiveIsFalse_ShouldSetUserInactive() {
        User inactiveUser = User.builder().id(1L).isActive(true).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(inactiveUser));
        when(userRepository.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.setActive(1L, false);

        assertFalse(inactiveUser.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).update(inactiveUser);
    }

    @Test
    void setActive_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.setActive(99L, true));

        verify(userRepository, times(1)).findById(99L);
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void newPassword_WhenUserExists_ShouldUpdatePassword() {
        User userWithOldPassword = User.builder().id(1L).password("oldPass").build();
        String newPassword = "newSecurePassword";
        when(userRepository.findById(1L)).thenReturn(Optional.of(userWithOldPassword));
        when(userRepository.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.newPassword(1L, newPassword);

        assertEquals(newPassword, userWithOldPassword.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).update(userWithOldPassword);
    }

    @Test
    void newPassword_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
        String newPassword = "newSecurePassword";
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.newPassword(99L, newPassword));

        verify(userRepository, times(1)).findById(99L);
        verify(userRepository, never()).update(any(User.class));
    }

    @Test
    void checkCredentials_WhenUserExistsAndPasswordsMatch_ShouldNotThrowException() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.checkCredentials(TEST_USERNAME, TEST_PASSWORD));

        verify(userRepository, times(1)).findByUsername(TEST_USERNAME);
    }

    @Test
    void checkCredentials_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.checkCredentials("nonexistent", TEST_PASSWORD));

        verify(userRepository, times(1)).findByUsername("nonexistent");
    }

    @Test
    void checkCredentials_WhenUserExistsAndPasswordsDoNotMatch_ShouldThrowRuntimeException() {
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> userService.checkCredentials(TEST_USERNAME, "wrongPassword"));

        verify(userRepository, times(1)).findByUsername(TEST_USERNAME);
    }
}