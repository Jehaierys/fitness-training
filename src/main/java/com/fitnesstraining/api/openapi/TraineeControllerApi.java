package com.fitnesstraining.api.openapi;

import com.fitnesstraining.domain.dto.abstraction.UpdateUserProfileRequest;
import com.fitnesstraining.domain.dto.trainee.request.TraineeSignUpRequest;
import com.fitnesstraining.domain.dto.abstraction.UserSignUpResponse;
import com.fitnesstraining.domain.dto.trainee.request.UpdateTraineeProfileRequest;
import com.fitnesstraining.domain.dto.trainee.response.UpdateTraineeProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
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
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trainee not found", content = @Content)
    })
    @PutMapping()
    ResponseEntity<UpdateTraineeProfileResponse> updateProfile(@Valid @RequestBody UpdateTraineeProfileRequest request);


}