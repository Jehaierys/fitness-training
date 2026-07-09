package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
public class Trainee extends User {

    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(nullable = true)
    private String address;

    @ManyToMany
    @JoinTable(
            name = "trainee_coach",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    @Builder.Default
    private Set<Coach> coaches = new HashSet<>();
}
