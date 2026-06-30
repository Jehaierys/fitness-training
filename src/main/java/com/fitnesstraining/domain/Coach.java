package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coachs")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coach_seq")
    @SequenceGenerator(name = "coach_seq", sequenceName = "coach_id_seq", allocationSize = 1)
    private Long id;
    private Long userId;
    private String specialization;
}