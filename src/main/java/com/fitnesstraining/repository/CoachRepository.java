package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Coach;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@RequiredArgsConstructor
public class CoachRepository {

    private final Map<Long, Coach> coachStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Coach save(Coach mentor) {
        if (mentor.getId() == null || mentor.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            mentor.setId(id);
        }
        coachStorage.put(mentor.getId(), mentor);
        return mentor;
    }

    public Coach findById(Long id) {
        return coachStorage.get(id);
    }

    public boolean existsById(Long id) {
        return coachStorage.containsKey(id);
    }
}