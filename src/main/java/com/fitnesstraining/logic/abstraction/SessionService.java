package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.domain.dto.session.SessionSearchCriteria;

import java.util.List;

public interface SessionService {

    Session create(Session session);

    Session getById(Long id);

}
