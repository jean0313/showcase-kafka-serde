package com.demo.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "demo", containerFactory = "kafkaListenerContainerFactory", groupId = "demo")
    public void receiveMsg(String message) {
        logger.info("received msg: {}", message);
    }
}