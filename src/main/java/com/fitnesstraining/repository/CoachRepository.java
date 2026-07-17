package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.Coach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.fitnesstraining.utils.ExceptionSuppliers.CoachNotFound;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CoachRepository {

    private final EntityManager entityManager;


    public Coach create(Coach coach) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(coach);
            transaction.commit();

            log.info("Coach with id: {} created", coach.getId());
            return coach;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public Coach update(Coach coach) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(coach);
            transaction.commit();

            log.info("Coach with id: {} updated", coach.getId());
            return coach;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public Optional<Coach> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Coach.class, id));
    }

    public Coach findByUsername(String username) {
        final String jpql = "SELECT c FROM Coach c WHERE c.username = :username";

        return entityManager
                .createQuery(jpql, Coach.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseThrow(CoachNotFound("Coach not found with username: " + username));
    }

    public boolean existsById(Long id) {
        return entityManager.find(Coach.class, id) != null;
    }

    public boolean existByUsername(String username) {
        final String jpql = "SELECT c FROM Coach c WHERE c.username = :username";

        return !entityManager
                .createQuery(jpql, Coach.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }

    public List<Coach> notAssignedOnTraineeWith(String username) {
        final String jpql = "SELECT c FROM Coach c WHERE c NOT IN (" +
                "SELECT t.coaches FROM Trainee t WHERE t.username = :traineeUsername" + ")";

        return entityManager
                .createQuery(jpql, Coach.class)
                .setParameter("traineeUsername", username)
                .setMaxResults(1)
                .getResultList();
    }
}