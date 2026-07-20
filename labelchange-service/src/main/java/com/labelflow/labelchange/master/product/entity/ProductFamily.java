package com.labelflow.labelchange.master.product.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_families")
public class ProductFamily extends BaseUuidEntity {

    @Column(name = "family_code", nullable = false, unique = true, length = 20)
    private String familyCode;

    @Column(name = "family_name", nullable = false, unique = true, length = 150)
    private String familyName;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "productFamily", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public static ProductFamily create(String familyCode, String familyName, String description) {
        return ProductFamily.builder()
                .familyCode(familyCode)
                .familyName(familyName)
                .description(description)
                .active(true)
                .build();
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
