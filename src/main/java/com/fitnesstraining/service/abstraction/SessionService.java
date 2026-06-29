package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Session;

public interface SessionService {

    public Session create(Session session);

    public Session getById(Long id);
}
