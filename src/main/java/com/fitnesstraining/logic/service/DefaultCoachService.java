package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.DefaultCoachRepository;
import com.fitnesstraining.logic.abstraction.CoachService;
import com.fitnesstraining.logic.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.CoachNotFound;
import static com.fitnesstraining.utils.ExceptionSuppliers.UserNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final DefaultCoachRepository coachRepository;


    public Coach create(User coach) {
        return coachRepository.create((Coach) coach);
    }

    public Coach getById(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(CoachNotFound("Coach not found with id: " + id));
    }

    public Coach update(User coach) {
        return coachRepository.update((Coach) coach);
    }

    public boolean existsByUsername(String username) {
        return coachRepository.existByUsername(username);
    }

    public Coach findByUsername(String username) {
        return coachRepository.findByUsername(username)
                .orElseThrow(UserNotFound("Coach not found with username: " + username));
    }

    public void newPassword(Long id, String newPassword) {
        Coach coach = coachRepository.findById(id).orElseThrow(UserNotFound("User not found with id: " + id));
        coach.setPassword(newPassword);
        coachRepository.update(coach);
        log.info("Password updated for user with id: {}", id);
    }
}