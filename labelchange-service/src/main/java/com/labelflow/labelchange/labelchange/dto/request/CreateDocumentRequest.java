package com.labelflow.labelchange.labelchange.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateDocumentRequest(

        @NotBlank(message = "Document Number is required.")
        @Size(max = 255)
        String documentNumber,

        @NotBlank(message = "Document Version is required.")
        @Size(max = 100)
        String documentVersion

) {
}