package com.demo.kafka.services;

import com.demo.kafka.annotations.ProducerHandler;
import com.demo.kafka.model.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaProducerService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Qualifier("kafkaJsonMsgTemplate")
    @Autowired
    private KafkaTemplate<String, PersonInfo> template;

    public void sendMessage(Message<PersonInfo> msg) {
        CompletableFuture<SendResult<String, PersonInfo>> send = template.send(msg);
        try {
            SendResult<String, PersonInfo> result = send.get();
            logger.info(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ProducerHandler
    public void sendMessage(String topic, PersonInfo message) {
        CompletableFuture<SendResult<String, PersonInfo>> send = template.send(topic, message);
        try {
            SendResult<String, PersonInfo> result = send.get();
            logger.info(result.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}