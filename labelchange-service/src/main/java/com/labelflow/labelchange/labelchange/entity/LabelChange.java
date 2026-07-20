package com.labelflow.labelchange.labelchange.entity;


import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.labelchange.enums.*;
import com.labelflow.labelchange.master.country.entity.Country;
import com.labelflow.labelchange.master.product.entity.Product;
import com.labelflow.labelchange.master.product.entity.ProductFamily;
import com.labelflow.labelchange.master.registration.entity.Registration;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "label_changes")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LabelChange extends BaseUuidEntity {

    @Column(name = "label_change_number", nullable = false, unique = true)
    private String labelChangeNumber;

    @Column(name = "trigger_date", nullable = false)
    private LocalDate triggerDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_type", nullable = false)
    private ChangeType changeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "process_impacted", nullable = false)
    private ProcessImpacted processImpacted;

    @Enumerated(EnumType.STRING)
    @Column(name = "change_category", nullable = false)
    private ChangeCategory changeCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_type", nullable = false)
    private TriggerType triggerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "signal", nullable = false)
    private SignalType signal;

    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "total_registrations", nullable = false)
    private Integer totalRegistrations;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabelChangeStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_family_id", updatable = false, nullable = false)
    private ProductFamily productFamily;

    @OneToMany(mappedBy = "labelChange", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<LabelChangeRegistration> labelChangeRegistrations = new HashSet<>();

    @OneToMany(mappedBy = "labelChange", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<LabelChangeProduct> labelChangeProducts = new HashSet<>();

    @OneToMany(mappedBy = "labelChange", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<LabelChangeCountry> labelChangeCountries = new HashSet<>();

    @OneToOne(mappedBy = "labelChange", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private LabelChangeDocument document;

    public static LabelChange create(
            String labelChangeNumber,
            ProductFamily productFamily,
            LocalDate triggerDate,
            LocalDate startDate,
            ChangeType changeType,
            ProcessImpacted processImpacted,
            ChangeCategory changeCategory,
            TriggerType triggerType,
            SignalType signal,
            String shortDescription,
            String description
    ) {

        return LabelChange.builder()
                .labelChangeNumber(labelChangeNumber)
                .productFamily(productFamily)
                .triggerDate(triggerDate)
                .startDate(startDate)
                .changeType(changeType)
                .processImpacted(processImpacted)
                .changeCategory(changeCategory)
                .triggerType(triggerType)
                .signal(signal)
                .shortDescription(shortDescription)
                .description(description)
                .status(LabelChangeStatus.NEW)
                .totalRegistrations(0)
                .build();
    }

    public void addRegistration(Registration registration) {

        if (containsRegistration(registration)) {
            return;
        }

        LabelChangeRegistration item = LabelChangeRegistration.create(this, registration);

        labelChangeRegistrations.add(item);

        totalRegistrations = labelChangeRegistrations.size();
    }

    public void addProduct(Product product) {

        if (containsProduct(product)) {
            return;
        }

        LabelChangeProduct item = LabelChangeProduct.create(this, product);

        labelChangeProducts.add(item);
    }

    public void addCountry(Country country) {

        if (containsCountry(country)) {
            return;
        }

        LabelChangeCountry item = LabelChangeCountry.create(this, country);

        labelChangeCountries.add(item);
    }

    public void start() {

        if (status != LabelChangeStatus.NEW) {
            throw new IllegalStateException("Only NEW label changes can be started.");
        }

        status = LabelChangeStatus.IN_PROGRESS;
    }

    public void complete() {

        if (status != LabelChangeStatus.IN_PROGRESS) {
            throw new IllegalStateException("Only IN_PROGRESS label changes can be completed.");
        }

        status = LabelChangeStatus.COMPLETED;
    }

    private boolean containsRegistration(Registration registration) {

        return labelChangeRegistrations.stream()
                .anyMatch(item ->
                        item.getRegistration().equals(registration));
    }

    private boolean containsProduct(Product product) {

        return labelChangeProducts.stream()
                .anyMatch(item ->
                        item.getProduct().equals(product));
    }

    private boolean containsCountry(Country country) {

        return labelChangeCountries.stream()
                .anyMatch(item ->
                        item.getCountry().equals(country));
    }

    public void proceed() {
        if (status != LabelChangeStatus.NEW) {
            throw new IllegalStateException("Only NEW label changes can be proceeded.");
        }

        status = LabelChangeStatus.IN_PROGRESS;
    }

    public void close() {
        if (status != LabelChangeStatus.NEW) {
            throw new IllegalStateException("Only NEW Label Changes can be closed.");
        }

        status = LabelChangeStatus.CLOSED;
    }

    public void attachDocument(LabelChangeDocument document) {
        this.document = document;
    }

}
