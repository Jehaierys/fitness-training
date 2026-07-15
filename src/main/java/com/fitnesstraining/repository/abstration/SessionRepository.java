package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.entity.Session;

import java.util.Optional;

public interface SessionRepository {

    Session create(Session session);

    Optional<Session> findById(Long id);

}
