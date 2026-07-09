package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Trainee;

public interface TraineeService extends UserService {

    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    void delete(Long id);

    void deleteByUsername(String username);
}
