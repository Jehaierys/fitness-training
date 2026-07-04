package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Coach;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CoachRepository {

    private final EntityManager entityManager;


    public Coach create(Coach coach) {
        entityManager.persist(coach);
        log.info("Coach with id: {} created", coach.getId());
        return coach;
    }

    public Coach update(Coach coach) {
        Coach mergedCoach = entityManager.merge(coach);
        log.info("Coach with id: {} updated", coach.getId());
        return mergedCoach;
    }

    public Optional<Coach> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Coach.class, id));
    }

    public Optional<Coach> findByUsername(String username) {
        String jpql = "SELECT c FROM Coach c WHERE c.user.username = :username";

        return entityManager
                .createQuery(jpql, Coach.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public boolean existsById(Long id) {
        return entityManager.find(Coach.class, id) != null;
    }

    public boolean existByUsername(String username) {
        String jpql = "SELECT c FROM Coach c WHERE c.user.username = :username";

        return !entityManager
                .createQuery(jpql, Coach.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}