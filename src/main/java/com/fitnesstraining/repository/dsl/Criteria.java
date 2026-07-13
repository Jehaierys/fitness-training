package com.fitnesstraining.repository.dsl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    private Integer limit = 25;
    private Integer offset = 0;
    private TypedQuery<T> query;


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

    public CriteriaQuery<T> query() {
        summarize();
        return criteriaQuery;
    }

    public List<T> list() {
        summarize();
        return query.getResultList();
    }

    public Criteria<T> limit(int limit) {
        this. limit= limit;
        return this;
    }

    public Criteria<T> offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Criteria<T> page(int page, int size) {
        this.offset = page * size;
        this.limit = size;
        return this;
    }

    private void summarize() {
        criteriaQuery.where(predicates.toArray(Predicate[]::new));

        query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(offset);
        query.setMaxResults(limit);
    }
}
