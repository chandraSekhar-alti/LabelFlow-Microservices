package com.labelflow.labelchange.kafka.producer;

import common.constants.KafkaTopics;
import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LabelChangeEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void publish(LabelChangeReadyForChangeOrderEvent event) {

    log.info("Publishing Label Change Event : {}", event.labelChangeNumber());
    log.info("Publishing Label kafka event : {}", event);

    kafkaTemplate.send(
        KafkaTopics.LABEL_CHANGE_READY_FOR_CHANGE_ORDER, event.labelChangeId().toString(), event);
  }
}
