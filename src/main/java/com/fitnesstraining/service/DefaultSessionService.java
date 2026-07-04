package com.fitnesstraining.service;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.dto.SessionSearchCriteria;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.service.abstraction.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.fitnesstraining.utils.ExceptionSuppliers.SessionNotFound;


@Service
@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    public Session create(Session session) {
        return sessionRepository.create(session);
    }

    public Session getById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(SessionNotFound("Session not found with id: " + id + "."));
    }

    public List<Session> searchSessions(SessionSearchCriteria criteria) {
        return sessionRepository.searchSessions(criteria);
    }
}
