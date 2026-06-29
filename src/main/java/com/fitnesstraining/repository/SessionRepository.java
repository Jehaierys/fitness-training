package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@RequiredArgsConstructor
public class SessionRepository {

    private final Map<Long, Session> sessionStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    private Session session;

    public Session save(Session session) {
        assignIdIfRequired(session);
        sessionStorage.put(session.getId(), session);
        return session;
    }

    private void assignIdIfRequired(Session session) {
        if (session.getId() == null || session.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            session.setId(id);
        }
    }

    public Optional<Session> findById(Long id) {
        return Optional.ofNullable(sessionStorage.get(id));
    }
}