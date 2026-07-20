package com.labelflow.labelchange.labelchange.entity;

import com.labelflow.labelchange.auth.entity.User;
import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.labelchange.enums.TrackingDecision;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "label_change_tracking_decisions")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabelChangeTrackingDecision extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "label_change_id", nullable = false)
    private LabelChange labelChange;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_decision", nullable = false)
    private TrackingDecision trackingDecision;

    @Column(length = 1000)
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "decided_by", nullable = false)
    private User decidedBy;

    @Column(name = "decided_at", nullable = false)
    private LocalDateTime decidedAt;

    public static LabelChangeTrackingDecision create(
            LabelChange labelChange,
            TrackingDecision trackingDecision,
            String comments,
            User decidedBy
    ) {

        return LabelChangeTrackingDecision.builder()
                .labelChange(labelChange)
                .trackingDecision(trackingDecision)
                .comments(comments)
                .decidedBy(decidedBy)
                .decidedAt(LocalDateTime.now())
                .build();
    }
}