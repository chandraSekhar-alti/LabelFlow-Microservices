package com.labelflow.labelchange.master.country.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseReferenceEntity;
import com.labelflow.labelchange.master.registration.entity.Registration;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "countries")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country extends BaseReferenceEntity {

    @Column(name = "country_code", nullable = false, unique = true, length = 5)
    private String countryCode;

    @Column(name = "country_name", nullable = false, unique = true, length = 100)
    private String countryName;

    @Column(name = "iso_code", nullable = false, unique = true, length = 3)
    private String isoCode;

    @Column(name = "region", length = 50)
    private String region;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    protected Boolean active = true;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Registration> registrations = new HashSet<>();

    public static Country create(Short id, String countryCode, String countryName, String isoCode, String region) {

        return Country.builder()
                .id(id)
                .countryCode(countryCode)
                .countryName(countryName)
                .isoCode(isoCode)
                .region(region)
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
