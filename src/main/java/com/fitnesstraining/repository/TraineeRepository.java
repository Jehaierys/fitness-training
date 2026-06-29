package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Trainee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@RequiredArgsConstructor
public class TraineeRepository {

    private final Map<Long, Trainee> traineeStorage;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Trainee save(Trainee trainee) {
        if (trainee.getId() == null || trainee.getId() == 0) {
            long id = idGenerator.getAndIncrement();
            trainee.setId(id);
        }
        traineeStorage.put(trainee.getId(), trainee);
        return trainee;
    }

    public Trainee findById(Long id) {
        return traineeStorage.get(id);
    }

    public boolean existsById(Long id) {
        return traineeStorage.containsKey(id);
    }

    public void deleteById(Long id) {
        traineeStorage.remove(id);
    }
}