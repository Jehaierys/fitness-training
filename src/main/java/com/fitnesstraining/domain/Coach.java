package com.fitnesstraining.domain;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coach {
    private Long id;
    private Long userId;
    private String specialization;
}