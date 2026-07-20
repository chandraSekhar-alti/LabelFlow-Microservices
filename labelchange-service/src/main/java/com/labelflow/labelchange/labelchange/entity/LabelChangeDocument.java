package com.labelflow.labelchange.labelchange.entity;

import com.labelflow.labelchange.common.exception.BusinessValidationException;
import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.labelchange.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Entity
@Table(
        name = "label_change_documents",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_lc_document",
                        columnNames = "label_change_id"
                )
        }
)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabelChangeDocument extends BaseUuidEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "label_change_id",
            nullable = false,
            updatable = false
    )
    private LabelChange labelChange;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(name = "document_version", nullable = false)
    private String documentVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_status", nullable = false)
    private DocumentStatus documentStatus;

    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_url", length = 2000)
    private String documentUrl;

    @Column(name = "actual_ccds_review_date")
    private LocalDate actualCcdsReviewDate;

    @Column(name = "actual_ccds_approval_date")
    private LocalDate actualCcdsApprovalDate;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "dispatch_date")
    private LocalDate dispatchDate;

    @Column(name = "dispatch_by")
    private String dispatchBy;

    public static LabelChangeDocument create(
            LabelChange labelChange,
            String documentNumber,
            String documentVersion
    ) {

        return LabelChangeDocument.builder()
                .labelChange(labelChange)
                .documentNumber(documentNumber)
                .documentVersion(documentVersion)
                .documentStatus(DocumentStatus.NEW)
                .documentType("CCDS")
                .build();
    }

    public void review(String documentUrl, LocalDate reviewDate) {

        if (documentStatus != DocumentStatus.NEW) {
            throw new BusinessValidationException("Only NEW documents can be reviewed.");
        }

        this.documentUrl = documentUrl;
        this.actualCcdsReviewDate = reviewDate;
        this.documentStatus = DocumentStatus.REVIEWED;
    }

    public void approve(LocalDate approvalDate) {

        if (documentStatus != DocumentStatus.REVIEWED) {
            throw new BusinessValidationException("Only REVIEWED documents can be approved.");
        }

        this.actualCcdsApprovalDate = approvalDate;
        this.documentStatus = DocumentStatus.APPROVED;
    }

    public void addEvent(String eventId) {

        if (documentStatus != DocumentStatus.APPROVED) {
            throw new BusinessValidationException("Event ID can only be added to an APPROVED document.");
        }

        if (this.eventId != null) {
            throw new BusinessValidationException("Event ID has already been assigned.");
        }

        this.eventId = eventId;
    }

    public void dispatch(String dispatchedBy) {

        if (documentStatus != DocumentStatus.APPROVED) {
            throw new BusinessValidationException("Only APPROVED documents can be dispatched.");
        }

        if (actualCcdsReviewDate == null) {
            throw new BusinessValidationException("Review Date is required.");
        }

        if (actualCcdsApprovalDate == null) {
            throw new BusinessValidationException("Approval Date is required.");
        }

        if (eventId == null) {
            throw new BusinessValidationException("Event ID is required.");
        }

        if (dispatchDate != null) {
            throw new BusinessValidationException("Document has already been dispatched.");
        }

        this.dispatchDate = LocalDate.now();
        this.dispatchBy = dispatchedBy;
    }

}