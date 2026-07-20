package com.labelflow.labelchange.common.exception;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(
        boolean success,
        String message,
        Map<String, String> validationErrors,
        LocalDateTime timestamp
) {
}
