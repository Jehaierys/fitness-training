package com.fitnesstraining.api.openapi;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "409", description = "Coach already exists", content = @Content)
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
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Coach not found", content = @Content)
    })
    @PutMapping()
    ResponseEntity<UpdateCoachProfileResponse> updateProfile(@Valid @RequestBody UpdateUserProfileRequest request);


}