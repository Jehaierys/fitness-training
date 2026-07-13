package com.fitnesstraining.api.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@Schema(description = "Стандартный ответ при ошибке")
public class ErrorResponse {

    @Schema(description = "Сообщение об ошибке", example = "Validation failed")
    private String message;

    @Schema(description = "Детализация ошибок по полям (если есть)")
    private Map<String, String> details;
}