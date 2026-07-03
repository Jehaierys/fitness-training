package com.fitnesstraining.service;

import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.UserRepository;
import com.fitnesstraining.service.exception.NotFoundException;
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

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("john.doe")
                .password("password123")
                .isActive(true)
                .build();
    }

    @Test
    void create_ShouldReturnSavedUser() {
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.create(user);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john.doe", result.getUsername());
        verify(userRepository, times(1)).save(user);
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
    void getById_WhenNotExists_ShouldThrowNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getById(99L));
        verify(userRepository, times(1)).findById(99L);
    }

    @Test
    void update_WhenExists_ShouldReturnUpdatedUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.update(user);
        assertNotNull(result);
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_WhenNotExists_ShouldThrowUserNotFoundException() {
        when(userRepository.existsById(1L)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.update(user));
        verify(userRepository, times(1)).existsById(1L);
        verify(userRepository, never()).save(any());
    }

    @Test
    void delete_WhenExists_ShouldCallRepositoryDelete() {
        long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);
        userService.delete(userId);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void delete_WhenNotExists_ShouldThrowUserNotFoundException() {
        long userId = 99L;
        when(userRepository.existsById(userId)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void existsByUsername_WhenExists_ShouldReturnTrue() {
        when(userRepository.existsByUsername("john.doe")).thenReturn(true);
        assertTrue(userService.existsByUsername("john.doe"));
        verify(userRepository, times(1)).existsByUsername("john.doe");
    }

    @Test
    void existsByUsername_WhenNotExists_ShouldReturnFalse() {
        when(userRepository.existsByUsername("nonexistent")).thenReturn(false);
        assertFalse(userService.existsByUsername("nonexistent"));
        verify(userRepository, times(1)).existsByUsername("nonexistent");
    }

    @Test
    void setActive_WhenUserExistsAndIsActiveIsTrue_ShouldSetUserActive() {
        User activeUser = User.builder().id(1L).isActive(false).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(activeUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.setActive(1L, true);

        assertTrue(activeUser.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(activeUser);
    }

    @Test
    void setActive_WhenUserExistsAndIsActiveIsFalse_ShouldSetUserInactive() {
        User inactiveUser = User.builder().id(1L).isActive(true).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(inactiveUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.setActive(1L, false);

        assertFalse(inactiveUser.isActive());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(inactiveUser);
    }

    @Test
    void setActive_WhenUserDoesNotExist_ShouldThrowNotFoundException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.setActive(99L, true));

        verify(userRepository, times(1)).findById(99L);
        verify(userRepository, never()).save(any(User.class));
    }
}