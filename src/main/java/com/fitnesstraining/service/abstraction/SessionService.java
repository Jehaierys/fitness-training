package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.dto.session.SessionSearchCriteria;

import java.util.List;

public interface SessionService {

    Session create(Session session);

    Session getById(Long id);

    List<Session> searchSessions(SessionSearchCriteria criteria);
}
