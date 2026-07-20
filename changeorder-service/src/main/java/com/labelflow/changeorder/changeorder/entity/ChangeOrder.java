package com.labelflow.changeorder.changeorder.entity;

import com.labelflow.changeorder.changeorder.enums.ChangeOrderStatus;
import com.labelflow.changeorder.common.constants.ServiceConstants;
import com.labelflow.changeorder.common.persistence.entity.BaseUuidEntity;
import common.event.changeorder.RegistrationSnapshot;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "change_orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class ChangeOrder extends BaseUuidEntity {

  @Column(nullable = false, unique = true)
  private String changeOrderNumber;

  @Column(nullable = false)
  private UUID labelChangeId;

  @Column(nullable = false)
  private String labelChangeNumber;

  @Column(nullable = false)
  private UUID registrationId;

  @Column(nullable = false)
  private String registrationNumber;

  @Column(nullable = false)
  private String countryCode;

  @Column(nullable = false)
  private String countryName;

  @Column(nullable = false)
  private UUID productFamilyId;

  @Column(nullable = false)
  private String productFamilyName;

  private String dispatchEventId;

  private LocalDateTime dispatchDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ChangeOrderStatus status;

  @Column(nullable = false)
  private String sourceService;

  public static ChangeOrder create(
      String changeOrderNumber,
      UUID labelChangeId,
      String labelChangeNumber,
      RegistrationSnapshot registration,
      String dispatchEventId,
      LocalDateTime dispatchDate) {

    return ChangeOrder.builder()
        .changeOrderNumber(changeOrderNumber)
        .labelChangeId(labelChangeId)
        .labelChangeNumber(labelChangeNumber)
        .registrationId(registration.registrationId())
        .registrationNumber(registration.registrationNumber())
        .countryCode(registration.countryCode())
        .countryName(registration.countryName())
        .productFamilyId(registration.productFamilyId())
        .productFamilyName(registration.productFamilyName())
        .dispatchEventId(dispatchEventId)
        .dispatchDate(dispatchDate)
        .status(ChangeOrderStatus.NEW)
        .sourceService(ServiceConstants.LABEL_CHANGE_SERVICE)
        .build();
  }
}
