package com.fitnesstraining.service.utils;

import com.fitnesstraining.domain.dto.response.coach.CoachDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Trainee;
import com.fitnesstraining.service.mapper.CoachMapper;
import com.fitnesstraining.repository.dsl.Criteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class CoachSearcher {

    @PersistenceContext
    private EntityManager entityManager;
    private final CoachMapper mapper;

    public List<CoachDto> findAvailable(Long traineeId) {
        final List<Coach> availableCoaches = fetchAvailableCoaches(traineeId);
        return mapToDto(availableCoaches);
    }

    private List<Coach> fetchAvailableCoaches(Long traineeId) {
        final Trainee traineeRef = entityManager.getReference(Trainee.class, traineeId);

        return Criteria.<Coach>of(entityManager)
                .root(Coach.class)
                .where((builder, root) -> builder.isTrue(root.get("isActive")))
                .where((builder, root) -> builder.isNotMember(traineeRef, root.get("trainees")))
                .list();
    }

    private List<CoachDto> mapToDto(List<Coach> availableCoaches) {
        return availableCoaches
                .stream()
                .map(mapper::toCoachDto)
                .toList();
    }
}
