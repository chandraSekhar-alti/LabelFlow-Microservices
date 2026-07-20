package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.DocumentStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AddEventResponse(

        UUID id,

        UUID labelChangeId,

        String labelChangeNumber,

        String eventId,

        DocumentStatus documentStatus

) {
}