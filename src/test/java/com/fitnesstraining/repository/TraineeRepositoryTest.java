package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TraineeRepositoryTest {

    @Autowired
    private TraineeRepository traineeRepository;

    private Trainee trainee;

    @BeforeEach
    void setUp() {
        trainee = new Trainee();
        trainee.setAddress("Nursultan Street, 15");
    }

    @Test
    void save_ShouldGenerateIdAndStoreTrainee() {
        Trainee saved = traineeRepository.save(trainee);
        assertNotNull(saved.getId());
        assertTrue(saved.getId() > 0);
        Trainee found = traineeRepository.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Nursultan Street, 15", found.getAddress());
    }

    @Test
    void existsById_ShouldReturnTrue_WhenTraineeExists() {
        Trainee saved = traineeRepository.save(trainee);
        assertTrue(traineeRepository.existsById(saved.getId()));
        assertFalse(traineeRepository.existsById(999L));
    }

    @Test
    void deleteById_ShouldRemoveTraineeFromStorage() {
        Trainee saved = traineeRepository.save(trainee);
        Long id = saved.getId();
        traineeRepository.deleteById(id);
        assertFalse(traineeRepository.existsById(id));
        assertNull(traineeRepository.findById(id));
    }
}