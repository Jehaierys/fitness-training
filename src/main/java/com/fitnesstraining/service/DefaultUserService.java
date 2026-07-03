package com.fitnesstraining.service;


import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.UserRepository;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.NotFoundException;
import com.fitnesstraining.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        log.info("Creating user with username: {}", user.getUsername());
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public User update(User user) {
        if (userRepository.existsById(user.getId())) {
            log.info("Updating user with id: {}", user.getId());
            return userRepository.save(user);
        } else {
            log.warn("User not found with id: {} to be updated", user.getId());
            throw new UserNotFoundException();
        }
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            log.warn("User not found with id: {} to be deleted", id);
            throw new UserNotFoundException("User not found with id: " + id);
        }
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void setActive(Long id, boolean isActive) {
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setActive(isActive);
            userRepository.save(user);
        }, () -> {
            throw new NotFoundException("User not found with id: " + id);
        });
        log.info("Set active status for user with id: {} to {}", id, isActive);
    }

    public void checkCredentials(String username, String password) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));

        if (!user.getPassword().equals(password)) {
            log.warn("User not found with username: {} and password: {}", username, user.getPassword());
            // todo: assign appropriate exception
            throw new RuntimeException("Invalid credentials for username: " + username);
        }
    }
}