package com.fitnesstraining.api.openapi;

import com.fitnesstraining.domain.dto.abstraction.Activated;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.domain.dto.trainee.response.GetTraineeResponse;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1.0.0/trainees")
@Tag(
        name = "Trainee Controller",
        description = "Operations related to trainee management"
)
public interface TraineeControllerApi {


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
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<UserSignUpResponse> signUp(@Valid @RequestBody TraineeSignUpRequest request);



    @Operation(
            summary = "Update trainee profile",
            description = "Updates personal details of the trainee."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Profile updated successfully",
                    content = @Content(schema = @Schema(implementation = UpdateTraineeProfileResponse.class))
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
                    responseCode = "404", description = "Trainee not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping()
    ResponseEntity<UpdateTraineeProfileResponse> update(@Valid @RequestBody UpdateTraineeProfileRequest request);



    @Operation(
            summary = "Delete trainee profile",
            description = "Deletes a trainee profile from the system using their username."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee successfully deleted"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Trainee not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{username}")
    ResponseEntity<HttpStatus> delete(
            @Parameter(description = "Username of the trainee to be deleted", required = true)
            @NotBlank
            @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
            @PathVariable String username);



    @Operation(
            summary = "Get trainee profile by username",
            description = "Retrieves full profile information for a specific trainee."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Trainee profile found",
                    content = @Content(schema = @Schema(implementation = GetTraineeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Trainee not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{username}")
    ResponseEntity<GetTraineeResponse> findByUsername(
            @Parameter(description = "Username of the trainee to fetch", required = true)
            //todo: to @QueryMapping, add validation
            @NotBlank
            @Size(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
            @PathVariable String username);




    @Operation(
            summary = "Set trainee active status",
            description = "Toggles the active/inactive status of a trainee profile."
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
                    responseCode = "404", description = "Trainee not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping()
    ResponseEntity<HttpStatus> setActive(@Valid @RequestBody Activated request);


}