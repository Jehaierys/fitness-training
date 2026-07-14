package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.dto.abstraction.Activated;
import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.coach.request.CoachSignUpRequest;
import com.fitnesstraining.domain.dto.coach.response.CoachDto;
import com.fitnesstraining.domain.dto.coach.response.GetCoachResponse;
import com.fitnesstraining.domain.dto.coach.response.UpdateCoachProfileResponse;
import com.fitnesstraining.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RequestMapping("/v1-0-0/coaches")
@Tag(
        name = "Coach",
        description = "Coach management"
)
public interface CoachControllerApi {


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
    ResponseEntity<UpdateCoachProfileResponse> update(@Valid @RequestBody UpdateUserProfileRequest request);



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
    @GetMapping("/{username}")
    ResponseEntity<GetCoachResponse> findByUsername(
            @Parameter(description = "Username of the coach to fetch", required = true)
            @NotBlank
            @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
            @PathVariable String username);



    @Operation(
            summary = "Set coach active status",
            description = "Toggles the active/inactive status of a coach profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated"),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
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
    @PatchMapping()
    ResponseEntity<HttpStatus> setActive(
            @Valid @RequestBody Activated request,
            @AuthenticationPrincipal UserDetails principal
    );



    @Operation(
            summary = "Get available coaches",
            description = "Returns a list of coaches available for the authenticated trainee."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Available coaches successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CoachDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/available")
    ResponseEntity<List<CoachDto>> findAvailableCoaches(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user
    );
}