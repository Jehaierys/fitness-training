package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.logic.abstraction.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;


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
        return coachRepository.findByUsername(username);
    }
}