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
import static com.fitnesstraining.utils.ExceptionSuppliers.UserNotFound;


@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTraineeService implements TraineeService {

    private final DefaultTraineeRepository traineeRepository;


    public Trainee create(User trainee) {
        return traineeRepository.create((Trainee) trainee);
    }

    public Trainee getById(Long id) {
        return traineeRepository.findById(id)
                .orElseThrow(TraineeNotFound("Trainee not found with id: " + id));
    }

    public Trainee update(User trainee) {
        return traineeRepository.update((Trainee) trainee);
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

    public boolean existsByUsername(String username) {
        return traineeRepository.existByUsername(username);
    }

    public Trainee findByUsername(String username) {
        return null;
    }

    public void setActive(Long id, boolean isActive) {
        traineeRepository.findById(id).ifPresentOrElse(trainee -> {
            trainee.setActive(isActive);
            traineeRepository.update(trainee);
            log.info("Set active status for user with id: {} to {}", id, isActive);
        }, () -> {
            throw new RuntimeException("User not found with id: " + id);
        });
    }

//
//    public void checkCredentials(String username, String password) {
//        User user = traineeRepository.findByUsername(username)
//                .orElseThrow(UserNotFound("User not found with username: " + username));
//
//        if (!user.getPassword().equals(password)) {
//            log.warn("User not found with username: {} and password: {}", username, user.getPassword());
//            // todo: assign appropriate exception
//            throw new RuntimeException("Invalid credentials for username: " + username);
//        }
//    }

    public void newPassword(Long id, String newPassword) {
        Trainee trainee = traineeRepository.findById(id).orElseThrow(UserNotFound("User not found with id:" + id));
        trainee.setPassword(newPassword);
        traineeRepository.update(trainee);
        log.info("Password updated for user with id: {}", id);
    }
}
