package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.Session;
import com.fitnesstraining.logic.abstraction.SessionService;
import com.fitnesstraining.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
