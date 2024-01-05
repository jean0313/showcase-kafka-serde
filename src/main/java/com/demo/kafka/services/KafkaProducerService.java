package com.demo.kafka.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducerService implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private ApplicationContext ctx;

    public <V> void sendMessage(String topic, V message) {
        // TODO pre-process

        // TODO validate
//
//        KafkaTemplate<String, V> template = (KafkaTemplate<String, V>) ctx.getBean(topic + "Template");
//        CompletableFuture<SendResult<String, V>> send = template.send(topic, message);
//        try {
//            SendResult<String, V> result = send.get();
//            // TODO post process
//            logger.info(result.toString());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}