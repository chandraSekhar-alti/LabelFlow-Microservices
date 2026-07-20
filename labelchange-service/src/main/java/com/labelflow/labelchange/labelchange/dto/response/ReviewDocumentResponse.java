package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.DocumentStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReviewDocumentResponse(

        UUID id,

        UUID labelChangeId,

        String labelChangeNumber,

        String documentNumber,

        String documentVersion,

        String documentUrl,

        LocalDate actualCcdsReviewDate,

        DocumentStatus documentStatus

) {
}