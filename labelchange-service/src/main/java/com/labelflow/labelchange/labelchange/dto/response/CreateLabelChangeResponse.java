package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.LabelChangeStatus;

import java.util.UUID;

public record CreateLabelChangeResponse(
        UUID id,

        String labelChangeNumber,

        LabelChangeStatus status,

        Integer totalRegistrations
) {
}
