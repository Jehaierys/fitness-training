package com.fitnesstraining.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;


public class Criteria<T> {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final List<Predicate> predicates;

    private CriteriaQuery<T> criteriaQuery;
    private Root<T> root;


    public static synchronized <T> Criteria<T> of(EntityManager entityManager) {
        return new Criteria<>(entityManager);
    }

    private Criteria(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.predicates = new ArrayList<>();
    }

    public Criteria<T> root(Class<T> entityClass) {
        this.criteriaQuery = criteriaBuilder.createQuery(entityClass);
        this.root = criteriaQuery.from(entityClass);
        return this;
    }

    public Criteria<T> where(BiFunction<CriteriaBuilder, Root<T>, Predicate> predicateFactory) {
        predicates.add(predicateFactory.apply(criteriaBuilder, root));
        return this;
    }

    public <J> Criteria<T> join(
            String attribute,
            BiFunction<CriteriaBuilder, Join<T, J>, Predicate> predicateFactory
    ) {
        Join<T, J> join = root.join(attribute);
        predicates.add(predicateFactory.apply(criteriaBuilder, join));
        return this;
    }

    public CriteriaQuery<T> build() {
        summarize();
        return criteriaQuery;
    }

    public List<T> list() {
        summarize();
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private void summarize() {
        criteriaQuery.where(predicates.toArray(Predicate[]::new));
    }
}
