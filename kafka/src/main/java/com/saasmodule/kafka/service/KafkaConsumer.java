package com.saasmodule.kafka.service;

import com.saasmodule.kafka.constants.KafkaConstants;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaConsumer implements ConsumerSeekAware {
  Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

  @Value("${kafka-read-position}")
  private String kafkaReadPosition;

  @Override
  public void onPartitionsAssigned(
      Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
    if (KafkaConstants.EARLIEST.equals(kafkaReadPosition)) {
      callback.seekToBeginning(assignments.keySet());
    } else {
      callback.seekToEnd(assignments.keySet());
    }
  }

  @KafkaListener(
      topics = "${spring.kafka.template.default-topic}",
      groupId = "${spring.kafka.consumer.group-id}")
  public void readMessage(String message) {
    logger.info("======Message======={}", message);
  }
}
