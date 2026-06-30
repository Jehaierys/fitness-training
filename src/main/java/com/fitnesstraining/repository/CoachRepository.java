package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Coach;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoachRepository extends JpaRepository<Coach, Long> {

    boolean existsById(Long id);

}