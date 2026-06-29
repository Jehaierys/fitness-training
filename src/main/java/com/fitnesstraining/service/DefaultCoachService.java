package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.abstraction.CoachService;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;

    public Coach create(Coach mentor) {
        return coachRepository.save(mentor);
    }

    public Optional<Coach> getById(Long id) {
        return coachRepository.findById(id);
    }

    public Coach update(Coach mentor) throws CoachNotFoundException {
        if (coachRepository.existsById(mentor.getId())) {
            return coachRepository.save(mentor);
        } else {
            throw new CoachNotFoundException("Mentor not found with id: " + mentor.getId());
        }
    }
}