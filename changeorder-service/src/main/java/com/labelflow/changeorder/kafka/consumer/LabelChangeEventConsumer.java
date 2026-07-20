package com.labelflow.changeorder.kafka.consumer;

import com.labelflow.changeorder.changeorder.service.ChangeOrderGenerationService;
import common.constants.KafkaTopics;
import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LabelChangeEventConsumer {

  private final ChangeOrderGenerationService generationService;

  @KafkaListener(
      topics = KafkaTopics.LABEL_CHANGE_READY_FOR_CHANGE_ORDER,
      groupId = "changeorder-group",
      containerFactory = "kafkaListenerContainerFactory")
  public void consume(LabelChangeReadyForChangeOrderEvent event) {

    log.info("Received Label Change Event : {}", event.labelChangeNumber());

    generationService.generate(event);
  }
}
