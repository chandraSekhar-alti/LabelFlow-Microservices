package com.labelflow.labelchange.labelchange.dto.response;

import com.labelflow.labelchange.labelchange.enums.LabelChangeStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TrackingDecisionResponse(

        UUID labelChangeId,

        String labelChangeNumber,

        LabelChangeStatus status

) {
}