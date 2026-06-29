package com.fitnesstraining.repository;

import com.fitnesstraining.domain.User;
import com.fitnesstraining.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;
    private Map<Long, User> userStorage;

    @BeforeEach
    void setUp() {
        userStorage = new HashMap<>();
        userRepository = new UserRepository(userStorage);
    }

    @Test
    void save_newUser_assignsIdAndSaves() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(1L, savedUser.getId());
        assertEquals("testuser", savedUser.getUsername());
        assertTrue(userStorage.containsKey(savedUser.getId()));
        assertEquals(savedUser, userStorage.get(savedUser.getId()));
    }

    @Test
    void save_existingUser_updatesUser() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");
        userRepository.save(user1); // ID will be 1

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("updateduser1");
        updatedUser.setPassword("newpass1");

        User result = userRepository.save(updatedUser);

        assertEquals(1L, result.getId());
        assertEquals("updateduser1", result.getUsername());
        assertEquals("newpass1", result.getPassword());
        assertEquals(1, userStorage.size()); // Should still be one user
        assertEquals(updatedUser, userStorage.get(1L));
    }

    @Test
    void findById_userExists_returnsUser() throws UserNotFoundException {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        userRepository.save(user); // ID will be 1

        User foundUser = userRepository.findById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    void findById_userDoesNotExist_throwsUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userRepository.findById(99L));
    }

    @Test
    void existsById_userExists_returnsTrue() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        userRepository.save(user); // ID will be 1

        assertTrue(userRepository.existsById(1L));
    }

    @Test
    void existsById_userDoesNotExist_returnsFalse() {
        assertFalse(userRepository.existsById(99L));
    }

    @Test
    void deleteById_userExists_removesUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        userRepository.save(user); // ID will be 1

        assertTrue(userRepository.existsById(1L));
        userRepository.deleteById(1L);
        assertFalse(userRepository.existsById(1L));
        assertTrue(userStorage.isEmpty());
    }

    @Test
    void deleteById_userDoesNotExist_doesNothing() {
        userRepository.deleteById(99L); // Should not throw an error
        assertTrue(userStorage.isEmpty());
    }

    @Test
    void existsByUsername_userExists_returnsTrue() {
        User user = new User();
        user.setUsername("existinguser");
        user.setPassword("password");
        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("existinguser"));
    }

    @Test
    void existsByUsername_userDoesNotExist_returnsFalse() {
        assertFalse(userRepository.existsByUsername("nonexistentuser"));
    }

    @Test
    void existsByUsername_multipleUsers_returnsTrueForExisting() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("pass2");
        userRepository.save(user2);

        assertTrue(userRepository.existsByUsername("user1"));
        assertTrue(userRepository.existsByUsername("user2"));
        assertFalse(userRepository.existsByUsername("user3"));
    }
}