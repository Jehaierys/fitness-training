package com.fitnesstraining.service;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.repository.TraineeRepository;
import com.fitnesstraining.service.abstraction.TraineeService;
import com.fitnesstraining.service.exception.TraineeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultTraineeService implements TraineeService {

    private final TraineeRepository traineeRepository;

    public Trainee create(Trainee trainee) {
        return traineeRepository.save(trainee);
    }

    public Trainee getById(Long id) {
        return traineeRepository.findById(id);
    }

    public Trainee update(Trainee trainee) {
        if (traineeRepository.existsById(trainee.getId())) {
            return traineeRepository.save(trainee);
        } else {
            throw new TraineeNotFoundException("Trainee not found with id: " + trainee.getId());
        }
    }

    public void delete(Long id) {
        if (!traineeRepository.existsById(id)) {
            throw new TraineeNotFoundException("Trainee not found with id: " + id);
        }
        traineeRepository.deleteById(id);
    }
}