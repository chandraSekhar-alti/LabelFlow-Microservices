package com.labelflow.labelchange.labelchange.entity;


import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.master.country.entity.Country;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "label_change_countries",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"label_change_id", "country_id"})
        })
public class LabelChangeCountry extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "label_change_id", nullable = false)
    private LabelChange labelChange;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    public static LabelChangeCountry create(LabelChange labelChange, Country country) {

        return LabelChangeCountry.builder()
                .labelChange(labelChange)
                .country(country)
                .build();
    }

}
