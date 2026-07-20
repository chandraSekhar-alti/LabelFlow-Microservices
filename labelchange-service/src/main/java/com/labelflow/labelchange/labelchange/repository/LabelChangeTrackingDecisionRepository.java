package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChangeTrackingDecision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelChangeTrackingDecisionRepository
        extends JpaRepository<LabelChangeTrackingDecision, UUID> {
}