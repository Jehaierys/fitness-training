package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserRequest;
import com.fitnesstraining.domain.entity.SessionType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateCoachRequest extends UpdateUserRequest {

    @NotEmpty(message = "Specializations list cannot be empty")
    List<SessionType> specialization;
}
