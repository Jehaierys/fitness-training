package com.fitnesstraining.domain.message;

public record CoachWorkload(
        int month,
        int year,
        int delta,
        Long coachId
) {
}