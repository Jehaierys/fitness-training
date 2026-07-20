package com.fitnesstraining.testUtils.dto;

import com.fitnesstraining.domain.dto.trainee.request.RegisterTraineeRequest;

import java.time.LocalDate;

public final class RegisterTraineeRequests {

    private RegisterTraineeRequests() {}



    public final static class Valid {

        private Valid() {}

        public static RegisterTraineeRequest withoutAddressAndBirthdate() {
            return RegisterTraineeRequest.builder()
                    .firstName("Sophia")
                    .lastName("Miller")
                    .username("sophia.miller")
                    .password("password123")
                    .build();
        }

        public static RegisterTraineeRequest fullData() {
            return RegisterTraineeRequest.builder()
                    .firstName("Sophia")
                    .lastName("Miller")
                    .username("sophia.miller")
                    .password("password123")
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane")
                    .build();
        }
    }



    public final static class Invalid {

        private Invalid() {}

        public static RegisterTraineeRequest missingCrucialData() {
            return RegisterTraineeRequest.builder()
                    .firstName(null)
                    .lastName(null)
                    .username(null)
                    .password(null)
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane")
                    .build();
        }

        // todo
        public static RegisterTraineeRequest weakPassword() {
            return null;
        }

        public static RegisterTraineeRequest tooShort() {
            return RegisterTraineeRequest.builder()
                    .username("abc") // min 4
                    .password("12345") // min 6
                    .firstName("A") // min 2
                    .lastName("B") // min 2
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("1234")  // min 5
                    .build();
        }

        public static RegisterTraineeRequest tooLong() {
            return RegisterTraineeRequest.builder()
                    .username("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa") // 31 chars
                    .password("p".repeat(101))
                    .firstName("A".repeat(51))
                    .lastName("B".repeat(51))
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("C".repeat(256))
                    .build();
        }

        public static RegisterTraineeRequest forbiddenCharacter() {
            return RegisterTraineeRequest.builder()
                    .username("@@@@") // size OK, pattern fails
                    .password("password123")
                    .firstName("John123") // pattern fails
                    .lastName("Smith!") // pattern fails
                    .birthDate(LocalDate.of(2001, 1, 30))
                    .address("73 River Lane") // valid
                    .build();
        }

        public static RegisterTraineeRequest invalidDate() {
            return RegisterTraineeRequest.builder()
                    .username("olivia.wilson")
                    .password("password123")
                    .firstName("Olivia")
                    .lastName("Wilson")
                    .birthDate(LocalDate.now().plusDays(1)) // @Past fails
                    .address("73 River Lane")
                    .build();
        }
    }
}
