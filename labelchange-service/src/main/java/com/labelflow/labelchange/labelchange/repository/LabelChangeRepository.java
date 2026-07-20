package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LabelChangeRepository extends JpaRepository<LabelChange, UUID> {

    Optional<LabelChange> findByLabelChangeNumber(String number);

}