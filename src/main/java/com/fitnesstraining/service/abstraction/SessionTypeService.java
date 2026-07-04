package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.SessionType;

public interface SessionTypeService {

    SessionType getById(Long id);

    SessionType create(SessionType sessionType);

    SessionType update(SessionType sessionType);

    void delete(SessionType sessionType);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existByName(String name);

}
