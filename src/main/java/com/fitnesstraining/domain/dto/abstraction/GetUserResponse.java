package com.fitnesstraining.domain.dto.abstraction;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class GetUserResponse {

    String username;
    boolean isActive;

}
