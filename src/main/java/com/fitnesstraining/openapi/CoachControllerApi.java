package com.fitnesstraining.openapi;

import com.fitnesstraining.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.dto.coach.response.CoachSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/coaches")
public interface CoachControllerApi {

    /**
     * Register a new trainee
     * Creates a new trainee account and associated user profile.
     */
    @Operation(
            summary = "Register a new trainee",
            description = "Creates a new trainee account and associated user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Coach successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping()
    ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody CoachSignUpRequest request);

}
