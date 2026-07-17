package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.Trainee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.fitnesstraining.utils.ExceptionSuppliers.TraineeNotFound;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TraineeRepository {

    private final EntityManager entityManager;


    public Trainee create(Trainee trainee) {

        final EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.persist(trainee);
            transaction.commit();

            // todo: add username
            log.info("Trainee with id: {} created", trainee.getId());
            return trainee;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public Trainee update(Trainee trainee) {

        if (trainee.getId() == null || !existsById(trainee.getId())) {
            log.warn("Trainee with id: {} not found for update", trainee.getId());
            throw new IllegalArgumentException("Trainee must have an ID and exist in the database to be updated.");
        }

        final EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.merge(trainee);
            transaction.commit();

            log.info("Trainee with id: {} updated", trainee.getId());
            return trainee;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Trainee.class, id));
    }

    public void delete(Trainee trainee) {

        final EntityTransaction transaction = entityManager.getTransaction();

        try {

            transaction.begin();
            entityManager.remove(trainee);
            transaction.commit();

            log.info("Trainee with id: {} deleted", trainee.getId());

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void deleteById(Long id) {
        findById(id).ifPresentOrElse(this::delete,
                () -> log.warn("Trainee with id: {} not found for deletion", id));
    }

    public boolean existsById(Long id) {
        return entityManager.find(Trainee.class, id) != null;
    }

    public Trainee findByUsername(String username) {
        final String jpql = "SELECT t FROM Trainee t WHERE t.username = :username";

        return entityManager
                .createQuery(jpql, Trainee.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElseThrow(TraineeNotFound("Trainee not found with username: " + username));
    }

    public boolean existByUsername(String username) {
        final String jpql = "SELECT t FROM Trainee t WHERE t.username = :username";

        return !entityManager
                .createQuery(jpql, Trainee.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}