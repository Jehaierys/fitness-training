package com.fitnesstraining.testUtils.dto;

import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeRequest;

import java.time.LocalDate;

public final class UpdateTraineeRequests {

    private UpdateTraineeRequests() {}

    public static final class Valid {

        private Valid() {}

        public static UpdateTraineeRequest fullData() {
            return UpdateTraineeRequest.builder()
                    .firstName("Sophia")
                    .lastName("Miller")
                    .username("sophia.miller")
                    .isActive(true)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane")
                    .build();
        }

        public static UpdateTraineeRequest withoutAddressAndBirthdate() {
            return UpdateTraineeRequest.builder()
                    .firstName("Sophia")
                    .lastName("Miller")
                    .username("sophia.miller")
                    .isActive(true)
                    .build();
        }
    }


    public static final class Invalid {

        private Invalid() {}

        public static UpdateTraineeRequest missingCrucialData() {
            return UpdateTraineeRequest.builder()
                    .firstName(null)
                    .lastName(null)
                    .username(null)
                    .isActive(null)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane")
                    .build();
        }

        public static UpdateTraineeRequest tooShort() {
            return UpdateTraineeRequest.builder()
                    .username("abc") // min 4
                    .firstName("A") // min 2
                    .lastName("B") // min 2
                    .isActive(true)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("1234")  // min 5
                    .build();
        }

        public static UpdateTraineeRequest tooLong() {
            return UpdateTraineeRequest.builder()
                    .username("a".repeat(31)) // max 30
                    .firstName("A".repeat(51)) // max 50
                    .lastName("B".repeat(51)) // max 50
                    .isActive(true)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("A".repeat(256)) // max 255
                    .build();
        }

        // todo constraint for this
        public static UpdateTraineeRequest unrealisticBirthDate() {
            return UpdateTraineeRequest.builder()
                    .username("sophia.miller")
                    .firstName("Sophia")
                    .lastName("Miller")
                    .isActive(true)
                    .birthDate(LocalDate.of(1800, 1, 1)) // unrealistic
                    .address("73 River Lane")
                    .build();
        }

        public static UpdateTraineeRequest forbiddenCharacters() {
            return UpdateTraineeRequest.builder()
                    .username("ol!v!@") // invalid symbol
                    .firstName("Olivi@") // invalid symbol
                    .lastName("W1lls#n") // invalid symbol
                    .isActive(true)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane")
                    .build();
        }

        public static UpdateTraineeRequest futureBirthdate() {
            return UpdateTraineeRequest.builder()
                    .username("olivia")
                    .firstName("Olivia")
                    .lastName("Wilson")
                    .isActive(true)
                    .birthDate(LocalDate.now().plusDays(1)) // @Past fails
                    .address("73 River Lane")
                    .build();
        }
    }
}
