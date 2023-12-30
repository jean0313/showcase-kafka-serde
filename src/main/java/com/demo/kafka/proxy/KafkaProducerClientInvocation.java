package com.demo.kafka.proxy;

import com.demo.kafka.producer.Postprocessor;
import com.demo.kafka.producer.Preprocessor;
import com.demo.kafka.producer.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class KafkaProducerClientInvocation implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerClientInvocation.class);

    private ApplicationContext applicationContext;

    public KafkaProducerClientInvocation(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        Object message = args[0];

        ProducerHandler annotation = method.getAnnotation(ProducerHandler.class);
        if (annotation == null) {
            return method.invoke(this, args);
        }
        String topic = annotation.topic();
        String validatorBeanName = annotation.validator();
        String preprocessorBeanName = annotation.preprocessor();
        String postprocessorBeanName = annotation.postprocessor();

        // TODO preprocessor
        Validator validator = (Validator) applicationContext.getBean(validatorBeanName);
        validator.validate(message);

        // TODO validator
        Preprocessor preprocessor = (Preprocessor) applicationContext.getBean(preprocessorBeanName);


        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(topic + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(topic, message);
        logger.info("thread: {}, topic: {},  producer result: {}", Thread.currentThread().getName(), topic, send.get());

        // TODO postprocessor
        Postprocessor postprocessor = (Postprocessor) applicationContext.getBean(postprocessorBeanName);
        return null;
    }
}
