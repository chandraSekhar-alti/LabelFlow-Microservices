package com.labelflow.labelchange.labelchange.repository;

import com.labelflow.labelchange.labelchange.entity.LabelChangeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LabelChangeProductRepository extends JpaRepository<LabelChangeProduct, UUID> {
}
