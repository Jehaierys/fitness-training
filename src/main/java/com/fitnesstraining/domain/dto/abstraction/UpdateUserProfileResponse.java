package com.fitnesstraining.domain.dto.abstraction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UpdateUserProfileResponse {

    String firstName;
    String lastName;
    String username;
    boolean isActive;
}
