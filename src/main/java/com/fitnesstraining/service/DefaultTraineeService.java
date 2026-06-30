package com.fitnesstraining.service;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.repository.TraineeRepository;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.exception.NotFoundException;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTraineeService implements TraineeService {

    private final TraineeRepository traineeRepository;

    public Trainee create(Trainee trainee) {
        log.info("Creating trainee with userId: {}", trainee.getUserId());
        return traineeRepository.save(trainee);
    }

    public Trainee getById(Long id) {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Trainee not found with id: " + id));
    }

    public Trainee update(Trainee trainee) {
        if (traineeRepository.existsById(trainee.getId())) {
            log.info("Updating trainee with id: {}", trainee.getId());
            return traineeRepository.save(trainee);
        } else {
            log.warn("Trainee not found with id: {} to be updated", trainee.getId());
            throw new TraineeNotFoundException("Trainee not found with id: " + trainee.getId());
        }
    }

    public void delete(Long id) {
        if (!traineeRepository.existsById(id)) {
            log.warn("Trainee not found with id: {} to be deleted", id);
            throw new TraineeNotFoundException("Trainee not found with id: " + id);
        }
        log.info("Deleting trainee with id: {}", id);
        traineeRepository.deleteById(id);
    }
}