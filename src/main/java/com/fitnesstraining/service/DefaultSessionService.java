package com.fitnesstraining.service;

import com.fitnesstraining.domain.Session;
import com.fitnesstraining.repository.SessionRepository;
import com.fitnesstraining.service.abstraction.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultSessionService implements SessionService {

    private final SessionRepository sessionRepository;

    public Session create(Session session) {
        log.info("Creating training session with id: {}", session.getId());
        return sessionRepository.save(session);
    }

    public Session getById(Long id) {
        log.info("Retrieving training session with id: {}", id);
        return sessionRepository.findById(id);
    }
}