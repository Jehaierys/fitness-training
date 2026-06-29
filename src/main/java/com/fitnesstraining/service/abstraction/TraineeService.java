package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Trainee;

public interface TraineeService {

    public Trainee create(Trainee trainee);

    public Trainee getById(Long id);

    public Trainee update(Trainee trainee);

    public void delete(Long id);

}
