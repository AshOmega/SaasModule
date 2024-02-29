package com.saasmodule.kafka.controller;

import com.saasmodule.kafka.configuration.KafkaTopicConfig;
import com.saasmodule.kafka.service.KafkaProducer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

  Logger logger = LoggerFactory.getLogger(KafkaController.class);

  @Value("${spring.kafka.template.default-topic}")
  private String topicName;

  @Autowired private KafkaProducer kafkaProducer;

  @PostMapping("/sendMessage")
  public String sendMessage(
      HttpServletRequest httpRequest, HttpServletResponse response, @RequestBody String message) {
    kafkaProducer.sendMessage(message, topicName);
    return "Success";
  }
}
