package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.Coach;

import java.util.Optional;

public interface CoachRepository {

    Coach create(Coach coach);

    Coach update(Coach coach);

    Optional<Coach> findById(Long id);

    Optional<Coach> findByUsername(String username);

    boolean existsById(Long id);

    boolean existByUsername(String username);

}
