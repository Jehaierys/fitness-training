package com.fitnesstraining.service;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.service.abstraction.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    public Optional<Session> getById(Long id) {
        return sessionRepository.findById(id);
    }
}