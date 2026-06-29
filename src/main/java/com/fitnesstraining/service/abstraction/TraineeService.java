package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.service.exception.TraineeNotFoundException;

import java.util.Optional;

public interface TraineeService {

    public Trainee create(Trainee trainee);

    public Optional<Trainee> getById(Long id);

    public Trainee update(Trainee trainee) throws TraineeNotFoundException;

    public void delete(Long id) throws TraineeNotFoundException;

}
