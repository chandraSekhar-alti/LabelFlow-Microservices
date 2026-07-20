package com.labelflow.changeorder.changeorder.service.impl;

import com.labelflow.changeorder.changeorder.entity.ChangeOrder;
import com.labelflow.changeorder.changeorder.repository.ChangeOrderRepository;
import com.labelflow.changeorder.changeorder.service.ChangeOrderGenerationService;
import com.labelflow.changeorder.sequence.BusinessCodeGenerator;
import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;
import common.event.changeorder.RegistrationSnapshot;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChangeOrderGenerationServiceImpl implements ChangeOrderGenerationService {

  private final ChangeOrderRepository changeOrderRepository;
  private final BusinessCodeGenerator businessCodeGenerator;

  @Override
  public void generate(LabelChangeReadyForChangeOrderEvent event) {

    log.info("Generating Change Orders for {}", event.labelChangeNumber());

    List<ChangeOrder> changeOrders =
        event.registrations().stream()
            .map(registration -> buildChangeOrder(event, registration))
            .toList();

    changeOrderRepository.saveAll(changeOrders);

    log.info("Generated {} Change Orders", changeOrders.size());
  }

  private ChangeOrder buildChangeOrder(
      LabelChangeReadyForChangeOrderEvent event, RegistrationSnapshot registration) {

    return ChangeOrder.create(
        businessCodeGenerator.generateChangeOrderNumber(),
        event.labelChangeId(),
        event.labelChangeNumber(),
        registration,
        event.dispatchEventId(),
        event.dispatchDate());
  }
}
