package com.fitnesstraining.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Coach {
    private Long id;
    private Long userId;
    private String specialization;
}