package com.fitnesstraining.utils;

import com.fitnesstraining.domain.entity.SessionType;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SessionTypes {

    @PersistenceContext
    private EntityManager entityManager;

    private List<SessionType> sessionTypes;

    private volatile boolean initialized = false;

    // cache
    private SessionType pilates;
    private SessionType strengthTraining;
    private SessionType zumba;
    private SessionType cardio;
    private SessionType yoga;
    private SessionType crossfit;


    public void initialize() {
        try {
            sessionTypes = entityManager
                    .createQuery("SELECT s FROM SessionType s", SessionType.class)
                    .getResultList();

            if (sessionTypes.size() != 6) {
                throw new RuntimeException("Expected 6 session types, but found " + sessionTypes.size());
            }
        } catch (Exception e) {
            log.error("Error occurred while initializing session types: {}", e.getMessage());
            throw e;
        }
    }


    public SessionType yoga() {
        if (!initialized) {
            initialize();
        }
        if (yoga == null) {
            yoga = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Yoga"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Yoga session type not found"));
        }
        return yoga;
    }

    public SessionType crossfit() {
        if (!initialized) {
            initialize();
        }
        if (crossfit == null) {
            crossfit = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Crossfit"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Crossfit session type not found"));
        }
        return crossfit;
    }

    public SessionType pilates() {
        if (!initialized) {
            initialize();
        }
        if (pilates == null) {
            pilates = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Pilates"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Pilates session type not found"));
        }
        return pilates;
    }

    public SessionType cardio() {
        if (!initialized) {
            initialize();
        }
        if (cardio == null) {
            cardio = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Cardio"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Cardio session type not found"));
        }
        return cardio;
    }

    public SessionType strengthTraining() {
        if (!initialized) {
            initialize();
        }
        if (strengthTraining == null) {
            strengthTraining = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Strength Training"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Strength Training session type not found"));
        }
        return strengthTraining;
    }

    public SessionType zumba() {
        if (!initialized) {
            initialize();
        }
        if (zumba == null) {
            zumba = sessionTypes.stream()
                    .filter(sessionType -> sessionType.getName().equals("Zumba"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Zumba session type not found"));
        }
        return zumba;
    }
}
