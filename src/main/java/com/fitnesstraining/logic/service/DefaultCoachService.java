package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.DefaultCoachRepository;
import com.fitnesstraining.logic.abstraction.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.UserNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final DefaultCoachRepository coachRepository;


    public Coach create(User coach) {
        return coachRepository.create((Coach) coach);
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
}