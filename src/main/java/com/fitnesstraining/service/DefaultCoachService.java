package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;

    public Coach create(Coach coach) {
        log.info("Creating coach with id: {}", coach.getId());
        return coachRepository.save(coach);
    }

    public Coach getById(Long id) {
        return coachRepository.findById(id);
    }

    public Coach update(Coach coach) {
        if (coachRepository.existsById(coach.getId())) {
            log.info("Updating coach with id: {}", coach.getId());
            return coachRepository.save(coach);
        } else {
            log.warn("Coach not found with id: {} to be updated", coach.getId());
            throw new CoachNotFoundException("Coach not found with id: " + coach.getId());
        }
    }
}