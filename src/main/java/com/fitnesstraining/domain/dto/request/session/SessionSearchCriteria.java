package com.fitnesstraining.domain.dto.request.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class SessionSearchCriteria {

    // todo: add lists of trainees and coaches

    Long requestSenderId;

    Long coachId;
    Long traineeId;
    Long sessionTypeId;


    @Past(message = "From date must be in the past")
    LocalDateTime from;

    @PastOrPresent(message = "To date must be in the past or present")
    LocalDateTime to;


    @Size(min = 4, max = 30, message = "Coach username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Coach username can only contain letters, numbers, dots, and underscores")
    String coachUsername;

    @Size(min = 4, max = 30, message = "Trainee username must be between 4 and 30 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = "Trainee username can only contain letters, numbers, dots, and underscores")
    String traineeUsername;


    @Size(min = 2, max = 50, message = "Coach first name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "Coach first name can only contain letters, hyphens, and spaces")
    String coachFirstName;

    @Size(min = 2, max = 50, message = "Coach last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "Coach last name can only contain letters, hyphens, and spaces")
    String coachLastName;

    @Size(min = 2, max = 50, message = "Coach first name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "Coach first name can only contain letters, hyphens, and spaces")
    String traineeFirstName;

    @Size(min = 2, max = 50, message = "Coach last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = "Coach last name can only contain letters, hyphens, and spaces")
    String traineeLastName;

}
