package com.fitnesstraining.testUtils;

import com.fitnesstraining.domain.entity.Coach;
import com.fitnesstraining.domain.entity.Trainee;

import java.time.LocalDate;
import java.util.List;

public final class Users {

    private Users() {}


    // Coaches

    public static Coach coachCarlos() {
        return Coach.builder()
                .id(100L)
                .firstName("Carlos")
                .lastName("Santana")
                .username("carlos")
                .password("password123")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.cardio(),
                                SessionTypes.yoga()
                        )
                )
                .build();
    }

    public static Coach coachAlice() {
        return Coach.builder()
                .id(101L)
                .firstName("Alice")
                .lastName("Johnson")
                .username("alice")
                .password("password123")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.yoga(),
                                SessionTypes.pilates(),
                                SessionTypes.cardio()
                        )
                )
                .build();
    }

    public static Coach coachBob() {
        return Coach.builder()
                .id(102L)
                .firstName("Bob")
                .lastName("Williams")
                .username("bob")
                .password("password123")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.zumba(),
                                SessionTypes.yoga()
                        )
                )
                .build();
    }

    public static Coach coachDavid() {
        return Coach.builder()
                .id(103L)
                .firstName("David")
                .lastName("Brown")
                .username("david")
                .password("password123")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.cardio(),
                                SessionTypes.crossfit(),
                                SessionTypes.strengthTraining()
                        )
                )
                .build();
    }

    public static Coach coachEmma() {
        return Coach.builder()
                .id(104L)
                .firstName("Emma")
                .lastName("Taylor")
                .username("emma")
                .password("password123")
                .isActive(true)
                .specialization(
                        List.of(
                                SessionTypes.strengthTraining(),
                                SessionTypes.zumba()
                        )
                )
                .build();
    }


    // Trainees

    public static Trainee traineeLucius() {
        return Trainee.builder()
                .id(105L)
                .firstName("Lucius")
                .lastName("Walker")
                .username("lucius")
                .password("password123")
                .isActive(true)
                .birthDate(LocalDate.of(1998, 3, 15))
                .address("12 Oak Street")
                .build();
    }

    public static Trainee traineeSophia() {
        return Trainee.builder()
                .id(106L)
                .firstName("Sophia")
                .lastName("Miller")
                .username("sophia")
                .password("password123")
                .isActive(true)
                .birthDate(LocalDate.of(2000, 7, 21))
                .address("45 Maple Avenue")
                .build();
    }

    public static Trainee traineeEthan() {
        return Trainee.builder()
                .id(107L)
                .firstName("Ethan")
                .lastName("Davis")
                .username("ethan")
                .password("password123")
                .isActive(true)
                .birthDate(LocalDate.of(1997, 11, 8))
                .address("8 Pine Road")
                .build();
    }

    public static Trainee traineeOlivia() {
        return Trainee.builder()
                .id(108L)
                .firstName("Olivia")
                .lastName("Wilson")
                .username("olivia")
                .password("password123")
                .isActive(false)
                .birthDate(LocalDate.of(2001, 1, 30))
                .address("73 River Lane")
                .build();
    }

    public static Trainee traineeNoah() {
        return Trainee.builder()
                .id(109L)
                .firstName("Noah")
                .lastName("Anderson")
                .username("noah")
                .password("password123")
                .isActive(true)
                .birthDate(LocalDate.of(1999, 9, 12))
                .address("101 Cedar Street")
                .build();
    }
}
