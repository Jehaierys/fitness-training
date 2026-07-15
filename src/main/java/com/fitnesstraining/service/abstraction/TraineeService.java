package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Trainee;

public interface TraineeService {

    Trainee create(Trainee trainee);

    Trainee getById(Long id);

    Trainee update(Trainee trainee);

    void delete(Long id);

    void deleteByUsername(String username);
}
