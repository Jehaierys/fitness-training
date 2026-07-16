package com.fitnesstraining.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;


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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_COACH")
        );
    }
}
