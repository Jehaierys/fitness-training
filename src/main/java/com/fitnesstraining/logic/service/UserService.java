package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) {
        log.info("Loading user by username: {}", username);
        return (UserDetails) repository.findByUsername(username);
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        repository.update(user);
        log.info("Password updated for user with id: {}", user.getId());
    }

    @Transactional
    public void setActive(Long id, Boolean active, User principal) {

        if (!principal.getId().equals(id)) {
            // todo: throw proper exception
            throw new RuntimeException("Unauthorized access");
        }

        final User user = repository.findById(id);

        user.setActive(active);
        log.info("User with id: {} set to active: {}", user.getId(), active);
    }

    @Transactional
    public void setActive(String username, Boolean active,  User principal) {

        if (principal.getUsername().equals(username)) {
            // todo: throw proper exception
            throw new RuntimeException("Unauthorized access");
        }

        final User user = repository.findByUsername(username);

        user.setActive(active);
        log.info("User with username: {} set to active: {}", user.getUsername(), active);
    }
}