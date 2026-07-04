package com.fitnesstraining.repository;

import com.fitnesstraining.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultUserRepository {

    private final EntityManager entityManager;

    public User create(User user) {
        entityManager.persist(user);
        log.info("User with id: {} created", user.getId());
        return user;
    }

    public User update(User user) {
        if (user.getId() == null || !existsById(user.getId())) {
            log.warn("User with id: {} not found for update", user.getId());
            throw new IllegalArgumentException("User must have an ID and exist in the database to be updated.");
        }
        User mergedUser = entityManager.merge(user);
        log.info("User with id: {} updated", user.getId());
        return mergedUser;
    }

    public void delete(User user) {
        log.info("Deleting user with id: {}", user.getId());
        entityManager.remove(user);
    }

    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    public Optional<User> findByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        return entityManager
                .createQuery(jpql, User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public boolean existsById(Long id) {
        return entityManager.find(User.class, id) != null;
    }

    public boolean existByUsername(String username) {
        String jpql = "SELECT u FROM User u WHERE u.username = :username";
        return !entityManager
                .createQuery(jpql, User.class)
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}