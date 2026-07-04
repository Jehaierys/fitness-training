package com.fitnesstraining.service;


import com.fitnesstraining.domain.User;
import com.fitnesstraining.repository.DefaultUserRepository;
import com.fitnesstraining.service.abstraction.UserService;
import com.fitnesstraining.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.UserNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final DefaultUserRepository userRepository;

    public User create(User user) {
        return userRepository.create(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFound("User not found with id: " + id));
    }

    public User update(User user) {
        return userRepository.update(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existByUsername(username);
    }

    public void setActive(Long id, boolean isActive) {
        userRepository.findById(id).ifPresentOrElse(user -> {
            user.setActive(isActive);
            userRepository.update(user);
            log.info("Set active status for user with id: {} to {}", id, isActive);
        }, () -> {
            throw new UserNotFoundException("User not found with id: " + id);
        });
    }

    public void checkCredentials(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFound("User not found with username: " + username));

        if (!user.getPassword().equals(password)) {
            log.warn("User not found with username: {} and password: {}", username, user.getPassword());
            // todo: assign appropriate exception
            throw new RuntimeException("Invalid credentials for username: " + username);
        }
    }

    // this method may be called straight from controller.
    // we can authenticate this request with UsernamePasswordAuthentication filter
    // then check for ids matching by jwt
    public void newPassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(UserNotFound("\"User not found with id: \" + id"));
        user.setPassword(newPassword);
        userRepository.update(user);
        log.info("Password updated for user with id: {}", id);
    }
}