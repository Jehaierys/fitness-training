package com.fitnesstraining.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Trainee {
    private Long id;
    private LocalDate birthDate;
    private String address;
    private Long userId;
}