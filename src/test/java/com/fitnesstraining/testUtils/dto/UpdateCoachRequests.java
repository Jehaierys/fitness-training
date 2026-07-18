package com.fitnesstraining.testUtils.dto;

import com.fitnesstraining.domain.dto.coach.request.UpdateCoachRequest;
import com.fitnesstraining.testUtils.entity.SessionTypes;

import java.util.List;

public final class UpdateCoachRequests {

    private UpdateCoachRequests() {}


    public static UpdateCoachRequest valid() {
        return UpdateCoachRequest.builder()
                .firstName("Carlos")
                .lastName("Santana")
                .username("carlos.santana")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.cardio(),
                                SessionTypes.yoga(),
                                SessionTypes.strengthTraining()
                        )
                )
                .build();
    }



    public static final class Invalid {

        private Invalid() {}

        public static UpdateCoachRequest missingCrucialData() {
            return UpdateCoachRequest.builder()
                    .firstName(null)
                    .lastName(null)
                    .username(null)
                    .isActive(null)
                    .specialization(null)
                    .build();
        }

        public static UpdateCoachRequest tooShort() {
            return UpdateCoachRequest.builder()
                    .firstName("A") // min 2
                    .lastName("B") // min 2
                    .username("abc") // min 4
                    .isActive(true)
                    .specialization(List.of(SessionTypes.cardio()))
                    .build();
        }

        public static UpdateCoachRequest tooLong() {
            return UpdateCoachRequest.builder()
                    .firstName("A".repeat(51)) // max 50
                    .lastName("B".repeat(51)) // max 50
                    .username("C".repeat(51)) // max 50
                    .isActive(true)
                    .specialization(List.of(SessionTypes.cardio()))
                    .build();
        }
    }
}
