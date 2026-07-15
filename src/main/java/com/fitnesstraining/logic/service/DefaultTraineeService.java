package com.fitnesstraining.logic.service;

import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.domain.entity.User;
import com.fitnesstraining.repository.DefaultTraineeRepository;
import com.fitnesstraining.logic.abstraction.TraineeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.fitnesstraining.utils.ExceptionSuppliers.TraineeNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTraineeService implements TraineeService {

    private final DefaultTraineeRepository repository;


    public Trainee create(User trainee) {
        return repository.create((Trainee) trainee);
    }

    public Trainee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(TraineeNotFound("Trainee not found with id: " + id));
    }

    public Trainee update(User trainee) {
        return repository.update((Trainee) trainee);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            log.warn("Trainee not found with id: {} to be deleted", id);
        } else {
            repository.deleteById(id);
        }
    }

    @Transactional
    public void deleteByUsername(String username) {
        repository.findByUsername(username).ifPresentOrElse(trainee -> {
            repository.delete(trainee);
            log.info("Trainee with username: {} deleted", username);
        }, () -> log.warn("Trainee not found with username: {} to be deleted", username));
    }

    public boolean existsByUsername(String username) {
        return repository.existByUsername(username);
    }

    public Trainee findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(TraineeNotFound("Trainee not found with username: " + username));
    }
}
