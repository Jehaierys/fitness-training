package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Coach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoachRepositoryTest {

    @Autowired
    private CoachRepository coachRepository;

    @Test
    void saveAndFind_ShouldCreateAndRetrieveCoach() {
        Coach coach = new Coach();
        coach.setSpecialization("Java Backend");
        Coach saved = coachRepository.save(coach);
        assertNotNull(saved.getId());
        Coach found = coachRepository.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Java Backend", found.getSpecialization());
    }

    @Test
    void save_WithExistingId_ShouldUpdateCoach() {Coach coach = new Coach();
        coach.setSpecialization("Spring Core");
        Coach saved = coachRepository.save(coach);
        Long id = saved.getId();
        saved.setSpecialization("Spring Boot & Data");
        Coach updated = coachRepository.save(saved);
        assertEquals(id, updated.getId());
        Coach found = coachRepository.findById(id);
        assertEquals("Spring Boot & Data", found.getSpecialization());
    }

    @Test
    void existsById_ShouldVerifyExistence() {
        Coach coach = new Coach();
        Coach saved = coachRepository.save(coach);
        assertTrue(coachRepository.existsById(saved.getId()));
    }
}