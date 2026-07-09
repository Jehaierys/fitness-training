package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.SessionType;

public interface SessionTypeService {

    SessionType getById(Long id);

    void delete(SessionType sessionType);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existByName(String name);

}
