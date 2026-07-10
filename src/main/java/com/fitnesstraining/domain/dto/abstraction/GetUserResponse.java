package com.fitnesstraining.domain.dto.abstraction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GetUserResponse {
    String username;
    String password;
    boolean isActive;
}
