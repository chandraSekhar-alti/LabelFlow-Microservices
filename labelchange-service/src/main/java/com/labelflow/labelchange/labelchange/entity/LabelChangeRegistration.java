package com.labelflow.labelchange.labelchange.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.master.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "label_change_registrations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"label_change_id", "registration_id"})
        })
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabelChangeRegistration extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "label_change_id", nullable = false)
    private LabelChange labelChange;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    public static LabelChangeRegistration create(LabelChange labelChange, Registration registration) {

        return LabelChangeRegistration.builder()
                .labelChange(labelChange)
                .registration(registration)
                .build();
    }
}