package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    boolean existsById(Long id);

}