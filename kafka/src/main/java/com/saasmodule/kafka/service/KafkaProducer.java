package com.saasmodule.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class KafkaProducer {
  Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message, String topicName) {
    CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
    future.whenComplete((result, ex) -> {
      if (ex == null) {
        logger.debug("Sent message={}, offset={}", message, result.getRecordMetadata().offset());
      } else {
        logger.error("Unable to send message={}",message, ex);
      }
    });
  }
}
