package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Session;

public interface SessionService {

    Session create(Session session);

    Session getById(Long id);
}
