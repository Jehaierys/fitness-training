package com.fitnesstraining.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trainee {
    private Long id;
    private LocalDate birthDate;
    private String address;
    private Long userId;
}