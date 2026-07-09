package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Coach;

public interface CoachService extends UserService {

    Coach create(Coach coach);

    Coach update(Coach coach);

}
