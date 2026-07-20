package com.labelflow.labelchange.master.product.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.master.registration.entity.Registration;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "products")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseUuidEntity {

    @Column(name = "product_code", nullable = false, unique = true, length = 20)
    private String productCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "family_id", nullable = false
    )
    private ProductFamily productFamily;

    @Column(name = "product_name", nullable = false, length = 200)
    private String productName;

    @Column(name = "product_type", length = 100)
    private String productType;

    @Column(name = "product_phase", length = 100)
    private String productPhase;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Registration> registrations = new HashSet<>();

    public static Product create(String productCode, ProductFamily productFamily, String productName, String productType, String productPhase) {

        return Product.builder()
                .productCode(productCode)
                .productFamily(productFamily)
                .productName(productName)
                .productType(productType)
                .productPhase(productPhase)
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