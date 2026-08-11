package com.fitnesstraining.repository;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.service.exception.SessionNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class SessionTypeRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public List<SessionType> findAll() {
        return entityManager.createQuery("SELECT st FROM SessionType st", SessionType.class)
                .getResultList();
    }

    public List<SessionType> findAllById(List<Integer> ids) {
        return entityManager
                .createQuery("""
                    SELECT s
                    FROM SessionType s
                    WHERE s.id IN :ids
                    """, SessionType.class)
                .setParameter("ids", ids)
                .getResultList();
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
            throw new SessionNotFoundException("SessionType with id: " + id + " not found for deletion.");
        });
    }

    public boolean existsById(Long id) {
        return entityManager.find(SessionType.class, id) != null;
    }

    public Optional<SessionType> findByName(String name) {
        final String jpql = "SELECT st FROM SessionType st WHERE st.name = :name";

        return entityManager.createQuery(jpql, SessionType.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
    }

    public boolean existByName(String name) {
        final String jpql = "SELECT st FROM SessionType st WHERE st.name = :name";

        return !entityManager.createQuery(jpql, SessionType.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getResultList()
                .isEmpty();
    }
}
