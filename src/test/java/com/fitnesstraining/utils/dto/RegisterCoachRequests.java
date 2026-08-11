package com.fitnesstraining.utils.dto;

import com.fitnesstraining.domain.dto.request.coach.RegisterCoachRequest;
import com.fitnesstraining.utils.entity.SessionTypes;

import java.util.List;

public final class RegisterCoachRequests {

    private RegisterCoachRequests() {}


    public static RegisterCoachRequest valid() {
        return RegisterCoachRequest.builder()
                .firstName("Carlos")
                .lastName("Santana")
                .username("carlos.santana")
                .password("password123")
                .specializationIds(
                        List.of(
                                SessionTypes.cardio().getId(),
                                SessionTypes.yoga().getId()
                        )
                )
                .build();
    }



    public static final class Invalid {

        private Invalid() {}

        public static RegisterCoachRequest missingCrucialData() {
            return RegisterCoachRequest.builder()
                    .firstName(null)
                    .lastName(null)
                    .username(null)
                    .password(null)
                    .specializationIds(null)
                    .build();
        }

        public static RegisterCoachRequest tooShort() {
            return RegisterCoachRequest.builder()
                    .firstName("A") // min 2
                    .lastName("B") // min 2
                    .username("abc") // min 4
                    .password("123") // min 6
                    .specializationIds(List.of(SessionTypes.cardio().getId()))
                    .build();
        }

        public static RegisterCoachRequest tooLong() {
            return RegisterCoachRequest.builder()
                    .firstName("A".repeat(51)) // max 50
                    .lastName("B".repeat(51)) // max 50
                    .username("C".repeat(51)) // max 50
                    .password("D".repeat(101)) // max 50
                    .specializationIds(List.of(SessionTypes.cardio().getId()))
                    .build();
        }

        public static RegisterCoachRequest forbiddenCharacters() {
            return RegisterCoachRequest.builder()
                    .firstName("John123") // pattern fails
                    .lastName("Smith!") // pattern fails
                    .username("@@@@") // pattern fails
                    .password("password123")
                    .specializationIds(List.of(SessionTypes.cardio().getId()))
                    .build();
        }
    }
}
