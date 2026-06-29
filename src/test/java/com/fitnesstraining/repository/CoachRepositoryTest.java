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
    void saveAndFind_ShouldCreateAndRetrieveMentor() {
        Coach mentor = new Coach();
        mentor.setSpecialization("Java Backend");
        Coach saved = coachRepository.save(mentor);
        assertNotNull(saved.getId());
        Coach found = coachRepository.findById(saved.getId());
        assertNotNull(found);
        assertEquals("Java Backend", found.getSpecialization());
    }

    @Test
    void save_WithExistingId_ShouldUpdateMentor() {Coach mentor = new Coach();
        mentor.setSpecialization("Spring Core");
        Coach saved = coachRepository.save(mentor);
        Long id = saved.getId();
        saved.setSpecialization("Spring Boot & Data");
        Coach updated = coachRepository.save(saved);
        assertEquals(id, updated.getId());
        Coach found = coachRepository.findById(id);
        assertEquals("Spring Boot & Data", found.getSpecialization());
    }

    @Test
    void existsById_ShouldVerifyExistence() {
        Coach mentor = new Coach();
        Coach saved = coachRepository.save(mentor);
        assertTrue(coachRepository.existsById(saved.getId()));
    }
}