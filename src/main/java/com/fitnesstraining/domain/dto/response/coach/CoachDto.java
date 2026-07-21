package com.fitnesstraining.domain.dto.response.coach;

import com.fitnesstraining.domain.entity.SessionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CoachDto {

    private String firstName;

    private String lastName;

    private String username;

    private List<SessionType> specialization;
}
