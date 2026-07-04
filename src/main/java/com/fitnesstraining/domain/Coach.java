package com.fitnesstraining.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    private Set<SessionType> specialization = new HashSet<>();

    @ManyToMany(mappedBy = "coaches")
    @Builder.Default
    private Set<Trainee> trainees = new HashSet<>();
}
