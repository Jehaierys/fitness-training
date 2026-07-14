package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthenticationControllerApi {


    @Operation(
            summary = "Register a new coach",
            description = "Creates a new coach account and associated user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Coach successfully registered",
                    content = @Content(schema = @Schema(implementation = UserSignUpResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Input data validation failed",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409", description = "User with this username already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping()
    ResponseEntity<UserSignUpResponse> registerCoach(@Valid @RequestBody CoachSignUpRequest request);



    @Operation(
            summary = "Register a new trainee",
            description = "Creates a new trainee account and associated user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Trainee successfully registered",
                    content = @Content(schema = @Schema(implementation = UserSignUpResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = org.springframework.web.ErrorResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<UserSignUpResponse> registerTrainee(@Valid @RequestBody TraineeSignUpRequest request);



    @Operation(
            summary = "Change password",
            description = "Updates password for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/password")
    void changePassword(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,
            @RequestParam String newPassword
    );

}
