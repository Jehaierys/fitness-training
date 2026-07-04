package com.fitnesstraining.repository.abstration;

import com.fitnesstraining.domain.SessionType;

import java.util.Optional;

public interface SessionTypeRepository {

    SessionType create(SessionType sessionType);

    SessionType update(SessionType sessionType);

    Optional<SessionType> findById(Long id);

    void delete(SessionType sessionType);

    void deleteById(Long id);

    boolean existsById(Long id);

    Optional<SessionType> findByName(String name);

    boolean existByName(String name);

}
