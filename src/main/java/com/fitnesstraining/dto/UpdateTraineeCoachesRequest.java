package com.fitnesstraining.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UpdateTraineeCoachesRequest {
    Long traineeId;
    Set<Long> coachIds;
}