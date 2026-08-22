package com.fitnesstraining.repository.rest;

import com.fitnesstraining.domain.dto.request.session.SessionRegistrationRequest;
import com.fitnesstraining.domain.entity.Coach;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;


// this microservice communication way should be replaced with Kafka

@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyticsRestClient {

    private final RestTemplate restTemplate;

    // Circuit Breaker from resilience4j
    // Chat GPT says Netflix's one is deprecated
    @CircuitBreaker(
            name = "analyticsService",
            fallbackMethod = "sendCoachWorkloadFallback"
    )
    public void sendCoachWorkload(
            SessionRegistrationRequest request,
            Coach coach
    ) {
        LocalDateTime date = request.getDate();
        Duration duration = request.getDuration();

        int year = date.getYear();
        int month = date.getMonthValue();

        // this value might be negative if a training session was canceled
        // but there is no logic to update a session (only create and read)
        // so I don't know where should I decrease coach's workload
        long delta = duration.toMinutes();

        String url = UriComponentsBuilder
                .fromPath("http://analytics:8082/coach/{id}/workload/{year}/{month}")
                .queryParam("delta", delta)
                .buildAndExpand(
                        coach.getId(),
                        year,
                        month
                )
                .toUriString();

        restTemplate.patchForObject(
                url,
                null,
                Void.class
        );
    }

    public void sendCoachWorkloadFallback(
            SessionRegistrationRequest request,
            Coach coach,
            Throwable throwable
    ) {
        log.warn(
                "Analytics service is unavailable. Coach workload was not sent. coachId={}",
                coach.getId(),
                throwable
        );
    }
}
