package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.Trainee;

import java.util.Optional;

public interface TraineeRepository {

    Trainee create(Trainee trainee);

    Trainee update(Trainee trainee);

    Optional<Trainee> findById(Long id);

    Optional<Trainee> findByUsername(String username);

    void delete(Trainee trainee);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existByUsername(String username);

}
