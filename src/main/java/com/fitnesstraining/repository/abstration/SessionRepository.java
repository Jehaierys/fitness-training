package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.dto.session.SessionSearchCriteria;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {

    Session create(Session session);

    Optional<Session> findById(Long id);

    List<Session> searchSessions(SessionSearchCriteria criteria);

}
