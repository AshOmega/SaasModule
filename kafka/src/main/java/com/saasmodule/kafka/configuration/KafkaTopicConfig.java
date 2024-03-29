package com.saasmodule.kafka.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

  Logger logger = LoggerFactory.getLogger(KafkaTopicConfig.class);

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value("${spring.kafka.template.default-topic}")
  private String topicName;

  @Bean
  public KafkaAdmin kafkaAdmin() {

    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    logger.debug("Bootstrap addresses={}", bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic topic1() {
    logger.debug("Creating topic!!!!");
    return new NewTopic(topicName, 1, (short) 1);
  }
}
