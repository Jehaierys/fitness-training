package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coaches")
public class Coach extends User {

    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    private Set<SessionType> specialization = new HashSet<>();

    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    private Set<Trainee> trainees = new HashSet<>();
}
