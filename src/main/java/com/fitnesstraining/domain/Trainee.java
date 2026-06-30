package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
public class Trainee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainees_seq")
    @SequenceGenerator(name = "trainees_seq", sequenceName = "trainees_id_seq", allocationSize = 1)
    private Long id;
    private LocalDate birthDate;
    private String address;
    private Long userId;
}