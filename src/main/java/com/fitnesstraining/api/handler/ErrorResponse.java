package com.fitnesstraining.api.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@Schema(description = "Standard error output")
public class ErrorResponse {

    @Schema(description = "Error message", example = "Validation failed")
    private String message;

    @Schema(description = "Error details")
    private Map<String, String> details;
}