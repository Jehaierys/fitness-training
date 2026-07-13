package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.Session;

public interface SessionService {

    Session create(Session session);

    Session getById(Long id);

}
