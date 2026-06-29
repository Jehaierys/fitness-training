package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Coach;

public interface CoachService {

    public Coach create(Coach coach);

    public Coach getById(Long id);

    public Coach update(Coach coach);
}
