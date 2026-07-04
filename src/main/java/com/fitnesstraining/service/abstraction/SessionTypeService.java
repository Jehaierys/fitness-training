package com.fitnesstraining.service.abstraction;

import com.fitnesstraining.domain.SessionType;

public interface SessionTypeService {

    SessionType getById(Long id);

    void delete(SessionType sessionType);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existByName(String name);

}
