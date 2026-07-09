package com.fitnesstraining.api.openapi;

import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/trainees")
public interface TraineeControllerApi {
    /**
     * Register a new trainee
     * Creates a new trainee account and associated user profile.
     */
    @Operation(
            summary = "Register a new trainee",
            description = "Creates a new trainee account and associated user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping()
    ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody TraineeSignUpRequest request);
}