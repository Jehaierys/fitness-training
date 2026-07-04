package com.fitnesstraining.repository;

import com.fitnesstraining.domain.SessionType;
import com.fitnesstraining.repository.abstration.SessionTypeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DefaultSessionTypeRepository implements SessionTypeRepository {

    private final EntityManager entityManager;


    public SessionType create(SessionType sessionType) {
        entityManager.persist(sessionType);
        log.info("SessionType with id: {} created", sessionType.getId());
        return sessionType;
    }

    public SessionType update(SessionType sessionType) {
        if (sessionType.getId() == null || !existsById(sessionType.getId())) {
            log.warn("SessionType with id: {} not found for update", sessionType.getId());
            throw new IllegalArgumentException("SessionType must have an ID and exist in the database to be updated.");
        }
        SessionType mergedSessionType = entityManager.merge(sessionType);
        log.info("SessionType with id: {} updated", sessionType.getId());
        return mergedSessionType;
    }

    public Optional<SessionType> findById(Long id) {
        return Optional.ofNullable(entityManager.find(SessionType.class, id));
    }

    public void delete(SessionType sessionType) {
        log.info("Deleting SessionType with id: {}", sessionType.getId());
        entityManager.remove(sessionType);
    }

    public void deleteById(Long id) {
        findById(id).ifPresentOrElse(this::delete, () -> {
            throw new IllegalArgumentException("SessionType with id: " + id + " not found for deletion.");
        });
    }

    public boolean existsById(Long id) {
        return entityManager.find(SessionType.class, id) != null;
    }

    public Optional<SessionType> findByName(String name) {
        String jpql = "SELECT st FROM SessionType st WHERE st.name = :name";

        return entityManager.createQuery(jpql, SessionType.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    public boolean existByName(String name) {
        String jpql = "SELECT st FROM SessionType st WHERE st.name = :name";

        return !entityManager.createQuery(jpql, SessionType.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}
