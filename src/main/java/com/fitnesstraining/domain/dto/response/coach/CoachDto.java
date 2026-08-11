package com.fitnesstraining.domain.dto.response.coach;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CoachDto {

    private String firstName;

    private String lastName;

    private String username;

    private int[] specializationIds;
}
