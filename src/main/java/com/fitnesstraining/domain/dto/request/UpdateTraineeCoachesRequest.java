package com.fitnesstraining.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Value;

import java.util.Collection;

@Value
@Builder
public class UpdateTraineeCoachesRequest {

    @Null(message = "traineeId must be null")
    Long traineeId;

    @NotEmpty(message = "Coach IDs cannot be empty")
    Collection<Long> coachIds;
}