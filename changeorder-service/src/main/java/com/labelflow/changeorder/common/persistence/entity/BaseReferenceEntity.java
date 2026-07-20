package com.labelflow.changeorder.common.persistence.entity;

import com.labelflow.changeorder.common.persistence.entity.AuditableEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseReferenceEntity extends AuditableEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    protected Short id;
}
