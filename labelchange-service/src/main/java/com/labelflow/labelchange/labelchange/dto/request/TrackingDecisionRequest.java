package com.labelflow.labelchange.labelchange.dto.request;

import com.labelflow.labelchange.labelchange.enums.TrackingDecision;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TrackingDecisionRequest(

        @NotNull
        TrackingDecision trackingDecision,

        @Size(max = 1000)
        String comments

) {
}