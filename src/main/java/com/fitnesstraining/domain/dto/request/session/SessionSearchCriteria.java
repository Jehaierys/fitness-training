package com.fitnesstraining.domain.dto.request.session;

import com.fitnesstraining.utils.ValidationErrorMessages;
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


    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String coachUsername;

    @Size(min = 4, max = 30, message = ValidationErrorMessages.Username.SIZE)
    @Pattern(regexp = "^[a-zA-Z0-9._]+$", message = ValidationErrorMessages.Username.PATTERN)
    String traineeUsername;


    @Size(min = 2, max = 50, message = ValidationErrorMessages.FirstName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.FirstName.PATTERN)
    String coachFirstName;

    @Size(min = 2, max = 50, message = ValidationErrorMessages.LastName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.LastName.PATTERN)
    String coachLastName;

    @Size(min = 2, max = 50, message = ValidationErrorMessages.FirstName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.FirstName.PATTERN)
    String traineeFirstName;

    @Size(min = 2, max = 50, message = ValidationErrorMessages.LastName.SIZE)
    @Pattern(regexp = "^[\\p{L}'\\-\\s]+$", message = ValidationErrorMessages.LastName.PATTERN)
    String traineeLastName;

}
