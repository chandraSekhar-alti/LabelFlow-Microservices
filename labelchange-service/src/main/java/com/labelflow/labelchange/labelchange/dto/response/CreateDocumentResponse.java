package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.DocumentStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateDocumentResponse(

        UUID id,

        UUID labelChangeId,

        String labelChangeNumber,

        String documentNumber,

        String documentVersion,

        DocumentStatus documentStatus,

        String documentType
) {
}