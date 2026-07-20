package com.labelflow.labelchange.labelchange.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddEventRequest(

        @NotBlank(message = "Event ID is required.")
        String eventId

) {
}