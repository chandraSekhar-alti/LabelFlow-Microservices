package com.labelflow.labelchange.labelchange.service;

import com.labelflow.labelchange.labelchange.dto.request.CreateLabelChangeRequest;
import com.labelflow.labelchange.labelchange.dto.request.TrackingDecisionRequest;
import com.labelflow.labelchange.labelchange.dto.response.CreateLabelChangeResponse;
import com.labelflow.labelchange.labelchange.dto.response.TrackingDecisionResponse;

import java.util.UUID;

public interface LabelChangeService {

    CreateLabelChangeResponse createLabelChange(CreateLabelChangeRequest request);

    TrackingDecisionResponse processTrackingDecision(UUID labelChangeId, TrackingDecisionRequest request);
}
