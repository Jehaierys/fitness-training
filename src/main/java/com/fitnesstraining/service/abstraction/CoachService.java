package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Coach;

public interface CoachService {

    Coach create(Coach coach);

    Coach getById(Long id);

    Coach update(Coach coach);
}
