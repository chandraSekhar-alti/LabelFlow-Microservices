package com.labelflow.labelchange.labelchange.controller;

import com.labelflow.labelchange.common.dto.ApiResponse;
import com.labelflow.labelchange.labelchange.dto.request.AddEventRequest;
import com.labelflow.labelchange.labelchange.dto.request.ApproveDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.CreateDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.ReviewDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.response.AddEventResponse;
import com.labelflow.labelchange.labelchange.dto.response.ApproveDocumentResponse;
import com.labelflow.labelchange.labelchange.dto.response.CreateDocumentResponse;
import com.labelflow.labelchange.labelchange.dto.response.ReviewDocumentResponse;
import com.labelflow.labelchange.labelchange.dto.response.DispatchDocumentResponse;
import com.labelflow.labelchange.labelchange.service.LabelChangeDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/label-changes")
public class LabelChangeDocumentController {

    private final LabelChangeDocumentService documentService;

    @PostMapping("/{id}/document")
    public ResponseEntity<ApiResponse<CreateDocumentResponse>> createDocument(@PathVariable UUID id, @Valid @RequestBody CreateDocumentRequest request) {

        CreateDocumentResponse response = documentService.createDocument(id, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<CreateDocumentResponse>builder()
                        .success(true)
                        .message("Document created successfully.")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{id}/document/review")
    public ResponseEntity<ApiResponse<ReviewDocumentResponse>> reviewDocument(@PathVariable UUID id, @Valid @RequestBody ReviewDocumentRequest request) {

        ReviewDocumentResponse response = documentService.reviewDocument(id, request);

        return ResponseEntity
                .ok(ApiResponse.<ReviewDocumentResponse>builder()
                        .success(true)
                        .message("Document reviewed successfully.")
                        .data(response)
                        .build());
    }

    @PatchMapping("/{id}/document/approve")
    public ResponseEntity<ApiResponse<ApproveDocumentResponse>> approveDocument(@PathVariable UUID id, @Valid @RequestBody ApproveDocumentRequest request) {

        ApproveDocumentResponse response = documentService.approveDocument(id, request);

        return ResponseEntity.ok(
                ApiResponse.<ApproveDocumentResponse>builder()
                        .success(true)
                        .message("Document approved successfully.")
                        .data(response)
                        .build()
        );
    }

    @PatchMapping("/{id}/document/event")
    public ResponseEntity<ApiResponse<AddEventResponse>> addEvent(@PathVariable UUID id, @Valid @RequestBody AddEventRequest request) {

        AddEventResponse response = documentService.addEvent(id, request);

        return ResponseEntity.ok(
                ApiResponse.<AddEventResponse>builder()
                        .success(true)
                        .message("Event added successfully.")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/{id}/document/dispatch")
    public ResponseEntity<ApiResponse<DispatchDocumentResponse>> dispatch(
            @PathVariable UUID id
    ) {

        DispatchDocumentResponse response = documentService.dispatch(id);

        return ResponseEntity.ok(
                ApiResponse.<DispatchDocumentResponse>builder()
                        .success(true)
                        .message("Document dispatched successfully.")
                        .data(response)
                        .build()
        );
    }

}