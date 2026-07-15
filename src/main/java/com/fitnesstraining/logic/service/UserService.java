package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.UserRepository;
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
}