package com.fitnesstraining.repository;

import com.fitnesstraining.domain.Trainee;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TraineeRepository {

    private final EntityManager entityManager;

    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        log.info("Trainee with id: {} created", trainee.getId());
        return trainee;
    }

    public Trainee update(Trainee trainee) {
        if (trainee.getId() == null || !existsById(trainee.getId())) {
            log.warn("Trainee with id: {} not found for update", trainee.getId());
            throw new IllegalArgumentException("Trainee must have an ID and exist in the database to be updated.");
        }
        Trainee mergedTrainee = entityManager.merge(trainee);
        log.info("Trainee with id: {} updated", trainee.getId());
        return mergedTrainee;
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Trainee.class, id));
    }

    public void delete(Trainee trainee) {
        entityManager.remove(trainee);
        log.info("Trainee with id: {} deleted", trainee.getId());
    }

    public void deleteById(Long id) {
        findById(id).ifPresentOrElse(this::delete,
                () -> {log.warn("Trainee with id: {} not found for deletion", id);});
    }

    public boolean existsById(Long id) {
        return entityManager.find(Trainee.class, id) != null;
    }

    public Optional<Trainee> findByUsername(String username) {
        String jpql = "SELECT t FROM Trainee t WHERE t.user.username = :username";

        return entityManager
                .createQuery(jpql, Trainee.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public boolean existByUsername(String username) {
        String jpql = "SELECT t FROM Trainee t WHERE t.user.username = :username";

        return !entityManager
                .createQuery(jpql, Trainee.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}