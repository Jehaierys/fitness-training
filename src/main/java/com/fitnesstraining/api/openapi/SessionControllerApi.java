package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.dto.session.GetCoachSessionDto;
import com.fitnesstraining.domain.dto.session.GetCoachSessionListRequest;
import com.fitnesstraining.domain.dto.session.SessionRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequestMapping("/sessions")
@Tag(
        name = "Session",
        description = "Operations related to training session scheduling and management"
)
public interface SessionControllerApi {

    @Operation(
            summary = "Create a new training session",
            description = "Registers a new training session in the system."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Session successfully created"),
            @ApiResponse(
                    responseCode = "400", description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    ResponseEntity<HttpStatus> create(@Valid @RequestBody SessionRegistrationRequest request);


    @Operation(summary = "Get coach sessions by criteria",
            description = "Retrieves a list of sessions for a specific coach based on various filtering criteria.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully retrieved list of sessions",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GetCoachSessionDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
            @ApiResponse(responseCode = "404", description = "Coach not found or no sessions matching criteria")
    })
    // будет передавать тело запроса, мне пофиг
    @GetMapping()
    ResponseEntity<List<GetCoachSessionDto>> getCoachSessionByCriteria(
            @Parameter(description = "Criteria to filter coach sessions", required = true)
            @Valid @RequestBody GetCoachSessionListRequest request
    );
}