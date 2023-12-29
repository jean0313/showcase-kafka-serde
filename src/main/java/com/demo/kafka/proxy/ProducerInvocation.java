package com.demo.kafka.proxy;

import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class ProducerInvocation implements InvocationHandler {

    private ApplicationContext applicationContext;

    public ProducerInvocation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ProducerInvocation() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoked:" + method.getName());
        for (Object arg : args) {
            System.out.println("arg type:" + arg.getClass() + ", arg value:" + arg);
        }
        String topic = (String) args[0];
        Object message = args[1];

        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(topic + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(topic, message);
        try {
            SendResult<String, Object> result = send.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
