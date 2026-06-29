package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;

    public Coach create(Coach coach) {
        return coachRepository.save(coach);
    }

    public Coach getById(Long id) {
        return coachRepository.findById(id);
    }

    public Coach update(Coach coach) {
        if (coachRepository.existsById(coach.getId())) {
            return coachRepository.save(coach);
        } else {
            throw new CoachNotFoundException("Coach not found with id: " + coach.getId());
        }
    }
}