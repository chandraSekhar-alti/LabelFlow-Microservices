package com.labelflow.labelchange.labelchange.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ApproveDocumentRequest(

        @NotNull(message = "Actual CCDS Approval Date is required.")
        LocalDate actualCcdsApprovalDate

) {
}
