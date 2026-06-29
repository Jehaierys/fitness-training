package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.service.exception.CoachNotFoundException;

import java.util.Optional;

public interface CoachService {

    public Coach create(Coach mentor);

    public Coach getById(Long id);

    public Coach update(Coach mentor);
}
