package com.labelflow.labelchange.labelchange.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReviewDocumentRequest(

        String documentUrl,

        @NotNull(message = "Actual CCDS Review Date is required.")
        LocalDate actualCcdsReviewDate

) {
}