package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.dto.SessionSearchCriteria;

import java.util.List;

public interface SessionService {

    Session create(Session session);

    Session getById(Long id);

    List<Session> searchSessions(SessionSearchCriteria criteria);
}
