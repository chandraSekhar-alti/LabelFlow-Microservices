package com.labelflow.labelchange.labelchange.service.impl;

import com.labelflow.labelchange.auth.entity.User;
import com.labelflow.labelchange.auth.entity.UserRole;
import com.labelflow.labelchange.auth.enums.RoleType;
import com.labelflow.labelchange.auth.repository.UserRepository;
import com.labelflow.labelchange.common.exception.BusinessValidationException;
import com.labelflow.labelchange.common.exception.ResourceNotFoundException;
import com.labelflow.labelchange.config.TimeConfig;
import com.labelflow.labelchange.kafka.producer.LabelChangeEventPublisher;
import com.labelflow.labelchange.labelchange.dto.request.AddEventRequest;
import com.labelflow.labelchange.labelchange.dto.request.ApproveDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.CreateDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.request.ReviewDocumentRequest;
import com.labelflow.labelchange.labelchange.dto.response.*;
import com.labelflow.labelchange.labelchange.entity.LabelChange;
import com.labelflow.labelchange.labelchange.entity.LabelChangeDocument;
import com.labelflow.labelchange.labelchange.entity.LabelChangeRegistration;
import com.labelflow.labelchange.labelchange.enums.LabelChangeStatus;
import com.labelflow.labelchange.labelchange.enums.ProcessImpacted;
import com.labelflow.labelchange.labelchange.repository.LabelChangeDocumentRepository;
import com.labelflow.labelchange.labelchange.repository.LabelChangeRepository;
import com.labelflow.labelchange.labelchange.service.LabelChangeDocumentService;
import com.labelflow.labelchange.master.registration.entity.Registration;
import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;
import common.event.changeorder.RegistrationSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LabelChangeDocumentServiceImpl implements LabelChangeDocumentService {

  private final LabelChangeRepository labelChangeRepository;
  private final LabelChangeDocumentRepository documentRepository;
  private final UserRepository userRepository;

  private final TimeConfig timeConfig;

  private final LabelChangeEventPublisher labelChangeEventPublisher;

  @Override
  public CreateDocumentResponse createDocument(UUID labelChangeId, CreateDocumentRequest request) {

    LabelChange labelChange = validateDocumentManagementAccess(labelChangeId);

    validateDocumentDoesNotExist(labelChangeId);

    LabelChangeDocument document = buildDocument(labelChange, request);

    documentRepository.save(document);

    return CreateDocumentResponse.builder()
        .id(document.getId())
        .labelChangeId(document.getLabelChange().getId())
        .labelChangeNumber(document.getLabelChange().getLabelChangeNumber())
        .documentNumber(document.getDocumentNumber())
        .documentVersion(document.getDocumentVersion())
        .documentStatus(document.getDocumentStatus())
        .build();
  }

  @Override
  public ReviewDocumentResponse reviewDocument(UUID labelChangeId, ReviewDocumentRequest request) {

    validateDocumentManagementAccess(labelChangeId);

    LabelChangeDocument document = getDocument(labelChangeId);

    document.review(request.documentUrl(), request.actualCcdsReviewDate());

    documentRepository.save(document);

    return ReviewDocumentResponse.builder()
        .id(document.getId())
        .labelChangeId(document.getLabelChange().getId())
        .labelChangeNumber(document.getLabelChange().getLabelChangeNumber())
        .documentNumber(document.getDocumentNumber())
        .documentVersion(document.getDocumentVersion())
        .documentUrl(document.getDocumentUrl())
        .actualCcdsReviewDate(document.getActualCcdsReviewDate())
        .documentStatus(document.getDocumentStatus())
        .build();
  }

  @Override
  public ApproveDocumentResponse approveDocument(
      UUID labelChangeId, ApproveDocumentRequest request) {

    validateDocumentManagementAccess(labelChangeId);

    LabelChangeDocument document = getDocument(labelChangeId);

    document.approve(request.actualCcdsApprovalDate());

    documentRepository.save(document);

    return ApproveDocumentResponse.builder()
        .id(document.getId())
        .labelChangeId(document.getLabelChange().getId())
        .labelChangeNumber(document.getLabelChange().getLabelChangeNumber())
        .documentNumber(document.getDocumentNumber())
        .documentVersion(document.getDocumentVersion())
        .actualCcdsReviewDate(document.getActualCcdsReviewDate())
        .actualCcdsApprovalDate(document.getActualCcdsApprovalDate())
        .documentStatus(document.getDocumentStatus())
        .build();
  }

  @Override
  public AddEventResponse addEvent(UUID labelChangeId, AddEventRequest request) {

    validateDocumentManagementAccess(labelChangeId);

    LabelChangeDocument document = getDocument(labelChangeId);

    document.addEvent(request.eventId());

    documentRepository.save(document);

    return AddEventResponse.builder()
        .id(document.getId())
        .labelChangeId(document.getLabelChange().getId())
        .labelChangeNumber(document.getLabelChange().getLabelChangeNumber())
        .eventId(document.getEventId())
        .documentStatus(document.getDocumentStatus())
        .build();
  }

  @Override
  public DispatchDocumentResponse dispatch(UUID labelChangeId) {

    User currentUser = getCurrentUser();

    LabelChange labelChange = validateDocumentManagementAccess(labelChangeId);

    LabelChangeDocument document = getDocument(labelChangeId);

    document.dispatch(currentUser.getEmail());

    labelChange.complete();

    documentRepository.save(document);

    labelChangeRepository.save(labelChange);

    List<RegistrationSnapshot> registrations = loadRegistrationSnapshots(labelChange);

    LabelChangeReadyForChangeOrderEvent event =
        new LabelChangeReadyForChangeOrderEvent(
            1,
            labelChange.getId(),
            labelChange.getLabelChangeNumber(),
            document.getEventId(),
            LocalDateTime.now(timeConfig.clock()),
            registrations);

    labelChangeEventPublisher.publish(event);

    return DispatchDocumentResponse.builder()
        .labelChangeId(document.getLabelChange().getId())
        .labelChangeNumber(document.getLabelChange().getLabelChangeNumber())
        .documentStatus(document.getDocumentStatus())
        .labelChangeStatus(document.getLabelChange().getStatus())
        .dispatchDate(document.getDispatchDate())
        .dispatchedBy(document.getDispatchBy())
        .build();
  }

  private List<RegistrationSnapshot> loadRegistrationSnapshots(LabelChange labelChange) {

    return labelChange.getLabelChangeRegistrations().stream()
        .map(LabelChangeRegistration::getRegistration)
        .map(this::toRegistrationSnapshot)
        .toList();
  }

  private RegistrationSnapshot toRegistrationSnapshot(Registration registration) {

    return new RegistrationSnapshot(
        registration.getId(),
        registration.getRegistrationNumber(),
        registration.getCountry().getCountryCode(),
        registration.getCountry().getCountryName(),
        registration.getProduct().getId(),
        registration.getProduct().getProductFamily().getFamilyName());
  }

  private LabelChange getLabelChange(UUID labelChangeId) {

    return labelChangeRepository
        .findById(labelChangeId)
        .orElseThrow(() -> new ResourceNotFoundException("Label Change not found."));
  }

  private void validateLabelChangeStatusInProgress(LabelChange labelChange) {

    if (labelChange.getStatus() != LabelChangeStatus.IN_PROGRESS) {
      throw new BusinessValidationException(
          "Document can only be created for an IN_PROGRESS Label Change.");
    }
  }

  private void validateDocumentDoesNotExist(UUID labelChangeId) {

    if (documentRepository.existsByLabelChange_Id(labelChangeId)) {
      throw new BusinessValidationException("Document already exists for this Label Change.");
    }
  }

  private LabelChangeDocument buildDocument(
      LabelChange labelChange, CreateDocumentRequest request) {

    return LabelChangeDocument.create(
        labelChange, request.documentNumber(), request.documentVersion());
  }

  private LabelChangeDocument getDocument(UUID labelChangeId) {
    return documentRepository
        .findByLabelChange_Id(labelChangeId)
        .orElseThrow(() -> new ResourceNotFoundException("Document not found."));
  }

  private User getCurrentUser() {

    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found."));
  }

  private void validateGlobalLabelChange(LabelChange labelChange) {
    if (labelChange.getProcessImpacted() != ProcessImpacted.GLOBAL) {
      throw new BusinessValidationException(
          "Document & Event Management is available only for Global Label Changes.");
    }
  }

  private void validateGLUser(User user) {

    boolean isGlUser =
        user.getUserRoles().stream()
            .map(UserRole::getRole)
            .anyMatch(role -> role.getRoleName() == RoleType.ROLE_GL);

    if (!isGlUser) {
      throw new BusinessValidationException(
          "Only GL users can perform Document & Event Management.");
    }
  }

  private LabelChange validateDocumentManagementAccess(UUID labelChangeId) {

    User currentUser = getCurrentUser();

    LabelChange labelChange = getLabelChange(labelChangeId);

    validateLabelChangeStatusInProgress(labelChange);

    validateGlobalLabelChange(labelChange);

    validateGLUser(currentUser);

    return labelChange;
  }
}
