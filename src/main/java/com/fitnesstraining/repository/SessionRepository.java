package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsById(Long id);

}
