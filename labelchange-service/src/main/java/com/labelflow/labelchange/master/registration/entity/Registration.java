package com.labelflow.labelchange.master.registration.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.master.country.entity.Country;
import com.labelflow.labelchange.master.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "registrations")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Registration extends BaseUuidEntity {

    @Column(name = "registration_code", nullable = false, unique = true, length = 30)
    private String registrationCode;

    @Column(name = "registration_number", length = 100)
    private String registrationNumber;

    @Column(name = "application_number", length = 100)
    private String applicationNumber;

    @Column(name = "trade_name", nullable = false, length = 200)
    private String tradeName;

    @Column(name = "package_name", length = 200)
    private String packageName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    public static Registration create(String registrationCode, String registrationNumber, String applicationNumber, String tradeName, String packageName, Product product, Country country) {

        return Registration.builder()
                .registrationCode(registrationCode)
                .registrationNumber(registrationNumber)
                .applicationNumber(applicationNumber)
                .tradeName(tradeName)
                .packageName(packageName)
                .product(product)
                .country(country)
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