package com.fitnesstraining.service.utils;

import com.fitnesstraining.domain.dto.response.coach.CoachDto;
import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.service.mapper.CoachMapper;
import com.fitnesstraining.repository.dsl.Criteria;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoachSearcher {

    private final CoachMapper mapper;


    public List<CoachDto> findAvailable(Long traineeId) {
        final List<Coach> availableCoaches = fetchAvailableCoaches(traineeId);
        return mapToDto(availableCoaches);
    }

    private List<Coach> fetchAvailableCoaches(Long traineeId) {
        return Criteria.<Coach>of()
                .root(Coach.class)
                .where((builder, root) -> builder.notEqual(root.get("traineeId"), traineeId))
                .where((builder, root) -> builder.isTrue(root.get("isActive")))
                .list();
    }

    private List<CoachDto> mapToDto(List<Coach> availableCoaches) {
        return availableCoaches
                .stream()
                .map(mapper::toCoachDto)
                .toList();
    }
}
