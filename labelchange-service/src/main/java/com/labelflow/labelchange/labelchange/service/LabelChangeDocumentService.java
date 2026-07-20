package com.labelflow.labelchange.labelchange.service;

import com.labelflow.labelchange.labelchange.dto.request.AddEventRequest;
import com.labelflow.labelchange.labelchange.dto.request.ApproveDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.CreateDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.ReviewDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.response.*;

import java.util.UUID;


public interface LabelChangeDocumentService {

    CreateDocumentResponse createDocument(UUID labelChangeId, CreateDocumentRequest request);

    ReviewDocumentResponse reviewDocument(UUID labelChangeId, ReviewDocumentRequest request);

    ApproveDocumentResponse approveDocument(UUID labelChangeId, ApproveDocumentRequest request);

    AddEventResponse addEvent(UUID labelChangeId, AddEventRequest request);

    DispatchDocumentResponse dispatch(UUID labelChangeId);
}
