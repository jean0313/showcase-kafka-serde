package com.demo.kafka.services;

import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "avro-topic", containerFactory = "avroListenerContainerFactory", groupId = "avro")
    public void receiveAvro(AvroPersonInfo message) {
        logger.info("{} received avro msg: name={}, age={}",Thread.currentThread().getName(), message.getName(), message.getAge());
    }

    @KafkaListener(topics = "demo", containerFactory = "jsonListenerContainerFactory", groupId = "demo")
    public void receiveJson(PersonInfo message) {
        logger.info("{} received json msg: name={}, age={}", Thread.currentThread().getName(), message.getName(), message.getAge());
    }

}