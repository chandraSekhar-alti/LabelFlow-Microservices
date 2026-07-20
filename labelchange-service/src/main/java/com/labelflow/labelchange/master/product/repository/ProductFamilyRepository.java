package com.labelflow.labelchange.master.product.repository;

import com.labelflow.labelchange.master.product.entity.ProductFamily;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFamilyRepository extends JpaRepository<ProductFamily, UUID> {

    Optional<ProductFamily> findByFamilyCode(String familyCode);

    Optional<ProductFamily> findByFamilyNameIgnoreCase(String familyName);

    boolean existsByFamilyCode(String familyCode);

    boolean existsByFamilyNameIgnoreCase(String familyName);

    List<ProductFamily> findByActiveTrueOrderByFamilyNameAsc();
}