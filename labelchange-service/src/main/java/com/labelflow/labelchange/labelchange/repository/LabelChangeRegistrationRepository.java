package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChangeRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelChangeRegistrationRepository extends JpaRepository<LabelChangeRegistration, UUID> {
}
