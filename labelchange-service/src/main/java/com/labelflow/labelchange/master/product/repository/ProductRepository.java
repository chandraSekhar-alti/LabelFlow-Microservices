package com.labelflow.labelchange.master.product.repository;

import com.labelflow.labelchange.master.product.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    List<Product> findByProductFamilyIdAndActiveTrueOrderByProductNameAsc(UUID familyId);

    List<Product> findByActiveTrueOrderByProductNameAsc();
}