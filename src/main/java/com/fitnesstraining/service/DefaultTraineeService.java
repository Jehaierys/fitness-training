package com.fitnesstraining.service;

import com.fitnesstraining.domain.Trainee;
import com.fitnesstraining.repository.DefaultTraineeRepository;
import com.fitnesstraining.service.abstraction.TraineeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.TraineeNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTraineeService implements TraineeService {

    private final DefaultTraineeRepository traineeRepository;

    public Trainee create(Trainee trainee) {
        return traineeRepository.create(trainee);
    }

    public Trainee getById(Long id) {
        return traineeRepository.findById(id)
                .orElseThrow(TraineeNotFound("Trainee not found with id: " + id));
    }

    public Trainee update(Trainee trainee) {
        return traineeRepository.update(trainee);
    }

    public void delete(Long id) {
        if (!traineeRepository.existsById(id)) {
            log.warn("Trainee not found with id: {} to be deleted", id);
        } else {
            traineeRepository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByUsername(String username) {
        traineeRepository.findByUsername(username).ifPresentOrElse(trainee -> {
            traineeRepository.delete(trainee);
            log.info("Trainee with username: {} deleted", username);
        }, () -> {
            log.warn("Trainee not found with username: {} to be deleted", username);
        });
    }
}
