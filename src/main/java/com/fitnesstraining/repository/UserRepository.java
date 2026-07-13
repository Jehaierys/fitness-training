package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User update(User user) {
        User mergedUser = entityManager.merge(user);
        log.info("User with id: {} updated", user.getId());
        return mergedUser;
    }
}
