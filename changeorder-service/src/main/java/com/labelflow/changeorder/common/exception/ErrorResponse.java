package com.labelflow.changeorder.common.exception;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;

@Builder
public record ErrorResponse(
        boolean success,
        String message,
        Map<String, String> validationErrors,
        LocalDateTime timestamp
) {
}
