package com.demo.kafka.framework;

import com.demo.kafka.model.EventDataModel;
import com.demo.kafka.producer.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

public class ProducerInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProducerInvocationHandler.class);

    private ApplicationContext applicationContext;

    public ProducerInvocationHandler(ApplicationContext applicationContext) {
        logger.info("create processor.");
        this.applicationContext = applicationContext;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.debug("proxy method invoke: {}, args: {}", method.getName(), args);
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }
        Object message = args[0];

        ProducerHandler annotation = method.getAnnotation(ProducerHandler.class);
        if (annotation == null) {
            throw new RuntimeException("producer method[" + method.getName() + "] must have ProducerHandler annotation");
        }
        String topic = annotation.topic();
        String validatorBeanName = annotation.validator();
        String preprocessorBeanName = annotation.preprocessor();
        String postprocessorBeanName = annotation.postprocessor();
        logger.debug("proxy method, topic: {}, validator: {}, preprocessor: {}, postprocessor: {}",
                topic,
                validatorBeanName,
                preprocessorBeanName,
                postprocessorBeanName);

        // TODO validator
        Validator<? extends EventDataModel> validator = getValidator(validatorBeanName);
        if (validator != null) {
            // validate
        }

        // TODO preprocessor

        // process
        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(topic + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(topic, message);
//        logger.info("thread: {}, topic: {},  producer result: {}", Thread.currentThread().getName(), topic, send.get());
        // TODO postprocessor
        return send;
    }

    private Validator<? extends EventDataModel> getValidator(String beanName) {
        try {
            return (Validator<? extends EventDataModel>) applicationContext.getBean(beanName);
        } catch (NoSuchBeanDefinitionException ex) {
            logger.warn("no validator defined, beanName: {}", beanName);
        }
        return null;
    }
}
