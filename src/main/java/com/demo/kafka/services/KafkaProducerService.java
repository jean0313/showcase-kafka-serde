package com.demo.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Qualifier("kafkaTextMsgTemplate")
    @Autowired
    private KafkaTemplate<String, String> template;

    public void sendMessage(String topic, String message) {
        CompletableFuture<SendResult<String, String>> send = template.send(topic, message);
        try {
            SendResult<String, String> result = send.get();
            logger.info(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}