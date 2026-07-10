package com.fitnesstraining.domain.entity;

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
@Table(name = "session_types")
public class SessionType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "session_type_seq")
    @SequenceGenerator(name = "session_type_seq", sequenceName = "session_type_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "coach_session_type",
            joinColumns = @JoinColumn(name = "session_type_id"),
            inverseJoinColumns = @JoinColumn(name = "coach_id")
    )
    @Builder.Default
    private Set<Coach> coaches = new HashSet<>();

}