package com.fitnesstraining.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class Coach {
    private Long id;
    private Long userId;
    private String specialization;
}