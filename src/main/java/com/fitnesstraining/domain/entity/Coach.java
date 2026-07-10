package com.fitnesstraining.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<SessionType> specialization = new ArrayList<>();

    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    private Set<Trainee> trainees = new HashSet<>();
}
