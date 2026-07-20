package com.labelflow.labelchange.labelchange.controller;

import com.labelflow.labelchange.common.dto.ApiResponse;
import com.labelflow.labelchange.labelchange.dto.request.CreateLabelChangeRequest;
import com.labelflow.labelchange.labelchange.dto.request.TrackingDecisionRequest;
import com.labelflow.labelchange.labelchange.dto.response.CreateLabelChangeResponse;
import com.labelflow.labelchange.labelchange.dto.response.TrackingDecisionResponse;
import com.labelflow.labelchange.labelchange.service.LabelChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/label-changes")
public class LabelChangeController {

    private final LabelChangeService labelChangeService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateLabelChangeResponse>> createLabelChange(@Valid @RequestBody CreateLabelChangeRequest request) {

        CreateLabelChangeResponse response = labelChangeService.createLabelChange(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<CreateLabelChangeResponse>builder()
                        .success(true)
                        .message("Label Change created successfully.")
                        .data(response)
                        .build());
    }

    @PostMapping("/{id}/tracking-decision")
    public ResponseEntity<ApiResponse<TrackingDecisionResponse>> processTrackingDecision(@PathVariable UUID id, @Valid @RequestBody TrackingDecisionRequest request) {

        TrackingDecisionResponse response = labelChangeService.processTrackingDecision(id, request);

        return ResponseEntity.
                ok(ApiResponse.<TrackingDecisionResponse>builder()
                        .success(true)
                        .message("Tracking decision processed successfully.")
                        .data(response)
                        .build());
    }
}