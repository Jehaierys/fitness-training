package com.fitnesstraining.logic.abstraction;

import com.fitnesstraining.domain.entity.SessionType;

import java.util.List;

public interface SessionTypeService {

    List<SessionType> findAll();

    SessionType findByName(String name);

    SessionType getById(Long id);

    void delete(SessionType sessionType);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existByName(String name);

}
