package com.fitnesstraining.domain.dto.coach.request;

import com.fitnesstraining.domain.entity.SessionType;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCoachProfileRequest extends UpdateUserProfileRequest {

    @NotEmpty(message = "Specializations list cannot be empty")
    Set<SessionType> specialization;
}
