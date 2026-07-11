package com.fitnesstraining.utils;

import com.fitnesstraining.domain.dto.session.GetCoachSessionDto;
import com.fitnesstraining.domain.dto.session.GetCoachSessionListRequest;
import com.fitnesstraining.domain.entity.Session;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionSearcher {

    private final EntityManager entityManager;
    private final SessionMapper mapper;

    private GetCoachSessionListRequest request;
    private UUID transactionUuid;
    private List<GetCoachSessionDto> response;
    private List<Session> sessions;

    public synchronized List<GetCoachSessionDto> search(GetCoachSessionListRequest request) {
        this.request = request;
        initialLog();

        findByCriteria();

        finalLog();
        return response;
    }

    private void initialLog() {
        transactionUuid = UUID.randomUUID();
        log.info("Searching sessions for coach: {} with criteria: {}, attempt's UUID: {}", request.getCoachUsername(), request, transactionUuid);
    }

    private void findByCriteria() {
        response = Criteria
                .<Session>of(entityManager)
                .root(Session.class)
                .where((cb, session) -> cb.between(
                        session.get("date"),
                        request.getFrom(),
                        request.getTo()
                ))
                .join("coach", (criteriaBuilder, coach)
                        -> criteriaBuilder.equal(coach.get("username"), request.getCoachUsername()))
                .join("trainee", (criteriaBuilder, trainee) -> {
                    if (request.getTraineeFirstName() != null) {
                        return criteriaBuilder.equal(trainee.get("firstName"), request.getTraineeFirstName());
                    } else {
                        return null;
                    }
                })
                .join("trainee", (criteriaBuilder, trainee) -> {
                    if (request.getTraineeLastName() != null) {
                        return criteriaBuilder.equal(trainee.get("lastName"), request.getTraineeLastName());
                    } else {
                        return null;
                    }
                })
                .list()
                .stream()
                .map(mapper::toGetCoachSessionDto)
                .toList();
    }

    private void finalLog() {
        log.info("Successfully found sessions for coach: {} with criteria: {}, process's UUID: {}", request.getCoachUsername(), request, transactionUuid);
    }
}
