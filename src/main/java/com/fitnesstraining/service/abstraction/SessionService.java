package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Session;

import java.util.Optional;

public interface SessionService {

    public Session create(Session session);

    public Optional<Session> getById(Long id);
}
