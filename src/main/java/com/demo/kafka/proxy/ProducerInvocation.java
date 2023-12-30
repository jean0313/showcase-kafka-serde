package com.demo.kafka.proxy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ProducerInvocation implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProducerInvocation.class);

    private ApplicationContext applicationContext;

    public ProducerInvocation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ProducerInvocation() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("invoked: {}", method.getName());
        for (Object arg : args) {
            logger.info("arg type:{}, arg value:{}", arg.getClass(), arg);
        }
        String topic = (String) args[0];
        Object message = args[1];

        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(topic + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(topic, message);
        send.whenComplete((result, ex) -> {
            if (ex != null) {
                logger.info("success with response: {}", result);
            } else {
                logger.info("failed");
            }
        }).get();
        return null;
    }
}
