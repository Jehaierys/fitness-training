package com.fitnesstraining.service;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.repository.CoachRepository;
import com.fitnesstraining.service.abstraction.CoachService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.CoachNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCoachService implements CoachService {

    private final CoachRepository coachRepository;

    public Coach create(Coach coach) {
        return coachRepository.create(coach);
    }

    public Coach getById(Long id) {
        return coachRepository.findById(id)
                .orElseThrow(CoachNotFound("Coach not found with id: " + id));
    }

    public Coach update(Coach coach) {
        return coachRepository.update(coach);
    }
}