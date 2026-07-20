package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChangeDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LabelChangeDocumentRepository extends JpaRepository<LabelChangeDocument, UUID> {

    boolean existsByLabelChange_Id(UUID labelChangeId);

    Optional<LabelChangeDocument> findByLabelChange_Id(UUID labelChangeId);

}