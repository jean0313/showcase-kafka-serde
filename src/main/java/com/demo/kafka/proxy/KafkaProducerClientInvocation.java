package com.demo.kafka.proxy;

import com.demo.kafka.framework.ProducerHandler;
import com.demo.kafka.producer.PostProcessor;
import com.demo.kafka.producer.PreProcessor;
import com.demo.kafka.producer.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
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
        String topic = "demo";
        String validatorBeanName = annotation.validator();
        String preprocessorBeanName = annotation.preprocessor();
        String postprocessorBeanName = annotation.postprocessor();

        // TODO preprocessor
        PreProcessor preprocessor = (PreProcessor) applicationContext.getBean(preprocessorBeanName);

        // TODO validator
        Validator validator = (Validator) applicationContext.getBean(validatorBeanName);
        validator.validate(message);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        // process
        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(topic + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(topic, message);
        logger.info("thread: {}, topic: {},  producer result: {}", Thread.currentThread().getName(), topic, send.get());
        // TODO postprocessor
        PostProcessor postprocessor = (PostProcessor) applicationContext.getBean(postprocessorBeanName);
        return null;
    }
}
