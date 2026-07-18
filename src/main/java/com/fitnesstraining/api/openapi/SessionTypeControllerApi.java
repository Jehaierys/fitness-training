package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.entity.SessionType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1-0-0/session-types")
@Tag(
        name = "Session Type",
        description = "Training session types and specializations"
)
public interface SessionTypeControllerApi {

    @Operation(
            summary = "Get all session types",
            description = "Retrieves a comprehensive list of all available training session types (specializations)."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successfully retrieved the list",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SessionType.class)))
            ),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    ResponseEntity<List<SessionType>> findAll();
}