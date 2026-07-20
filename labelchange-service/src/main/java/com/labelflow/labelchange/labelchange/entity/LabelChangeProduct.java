package com.labelflow.labelchange.labelchange.entity;

import com.labelflow.labelchange.common.persistence.entity.BaseUuidEntity;
import com.labelflow.labelchange.master.product.entity.Product;
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
@Table(name = "label_change_products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"label_change_id", "product_id"})
        })
public class LabelChangeProduct extends BaseUuidEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "label_change_id", nullable = false)
    private LabelChange labelChange;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public static LabelChangeProduct create(LabelChange labelChange, Product product) {

        return LabelChangeProduct.builder()
                .labelChange(labelChange)
                .product(product)
                .build();
    }
}
