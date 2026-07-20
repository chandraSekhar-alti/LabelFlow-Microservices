package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.DocumentStatus;
import com.labelflow.labelchange.labelchange.enums.LabelChangeStatus;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record DispatchDocumentResponse(

        UUID labelChangeId,

        String labelChangeNumber,

        DocumentStatus documentStatus,

        LabelChangeStatus labelChangeStatus,

        LocalDate dispatchDate,

        String dispatchedBy

) {
}
