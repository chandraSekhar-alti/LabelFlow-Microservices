package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChangeCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelChangeCountryRepository extends JpaRepository<LabelChangeCountry, UUID> {
}
