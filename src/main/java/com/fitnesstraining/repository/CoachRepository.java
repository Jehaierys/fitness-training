package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Coach;
import com.fitnesstraining.service.exception.CoachNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@RequiredArgsConstructor
public class CoachRepository {

    private final Map<Long, Coach> coachStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Coach save(Coach coach) {
        if (coach.getId() == null || coach.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            coach.setId(id);
        }
        coachStorage.put(coach.getId(), coach);
        return coach;
    }

    public Coach findById(Long id) {
        Coach coach = coachStorage.get(id);
        if (coach == null) {
            throw new CoachNotFoundException("Coach not found with id: " + id);
        }
        return coach;
    }

    public boolean existsById(Long id) {
        return coachStorage.containsKey(id);
    }
}