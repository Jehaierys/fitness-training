package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequestMapping("/coaches")
@Tag(
        name = "Coach Controller",
        description = "Operations related to coach management"
)
public interface CoachControllerApi {


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
    ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody CoachSignUpRequest request);



    @Operation(
            summary = "Update coach profile",
            description = "Updates personal details and professional info of the coach."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Profile updated successfully",
                    content = @Content(schema = @Schema(implementation = UpdateCoachProfileResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Coach profile not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping()
    ResponseEntity<UpdateCoachProfileResponse> updateProfile(@Valid @RequestBody UpdateUserProfileRequest request);


    @Operation(
            summary = "Get coach profile by username",
            description = "Retrieves full profile information for a specific coach."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Coach profile found",
                    content = @Content(schema = @Schema(implementation = GetCoachResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Coach not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    ResponseEntity<GetCoachResponse> findByUsername(
            @Parameter(description = "Username of the coach to fetch", required = true)
            @RequestParam String username);

}