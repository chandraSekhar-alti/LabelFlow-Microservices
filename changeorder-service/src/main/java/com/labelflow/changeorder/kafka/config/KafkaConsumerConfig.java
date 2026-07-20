package com.labelflow.changeorder.kafka.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import common.event.changeorder.LabelChangeReadyForChangeOrderEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

  @Bean
  public ConsumerFactory<String, LabelChangeReadyForChangeOrderEvent> consumerFactory(
      KafkaProperties properties) {

    Map<String, Object> config = properties.buildConsumerProperties();

    JsonDeserializer<LabelChangeReadyForChangeOrderEvent> deserializer =
        new JsonDeserializer<>(LabelChangeReadyForChangeOrderEvent.class);

    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeHeaders(false);

    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, LabelChangeReadyForChangeOrderEvent>
      kafkaListenerContainerFactory(
          ConsumerFactory<String, LabelChangeReadyForChangeOrderEvent> consumerFactory) {

    ConcurrentKafkaListenerContainerFactory<String, LabelChangeReadyForChangeOrderEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory);

    return factory;
  }
}
