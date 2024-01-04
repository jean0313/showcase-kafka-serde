package com.demo.kafka.framework;

import com.demo.kafka.model.EventDataModel;
import com.demo.kafka.producer.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ProducerInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(ProducerInvocationHandler.class);

    private ApplicationContext applicationContext;

    public ProducerInvocationHandler(ApplicationContext applicationContext) {
        logger.info("create processor.");
        this.applicationContext = applicationContext;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.debug("proxy method invoke: {}, args: {}", method.getName(), args);
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        Object message = args[0];
        if (!(message instanceof Message<?>)) {
            throw new RuntimeException("producer method[" + method.getName() + "] only support  org.springframework.messaging.Message");
        }
        KafkaProducerModel<?> model = buildModel(method, (Message<?>) message);

        // process
        KafkaTemplate<String, Object> template = (KafkaTemplate<String, Object>) applicationContext.getBean(model.getTopic() + "Template");
        CompletableFuture<SendResult<String, Object>> send = template.send(model.getMessage());
        logger.info("thread: {}, topic: {},  producer result: {}", Thread.currentThread().getName(), model.getTopic(), send.get());

        String handlerName = model.getPostprocessor();
        ResponseHandler handler = (ResponseHandler) getBean(handlerName);
        send.whenComplete((r, ex) -> {
            if (ex == null) {
                handler.onSuccess(r);
            } else {
                handler.onFailure(ex);
            }
        });
        if (args.length == 2) {
            Object arg = args[1];
            Assert.isTrue(arg instanceof Duration, "block request should use Duration to set wait timeout");

            Duration duration = Duration.ofSeconds(10);
            return send.get(duration.getSeconds(), TimeUnit.SECONDS);
        } else {
            return send;
        }
    }

    private <T> KafkaProducerModel<T> buildModel(Method method, Message<T> message) {
        ProducerHandler annotation = method.getAnnotation(ProducerHandler.class);
        if (annotation == null) {
            throw new RuntimeException("producer method[" + method.getName() + "] must have ProducerHandler annotation");
        }
        logger.debug("proxy method, validator: {}, preprocessor: {}, postprocessor: {}",
                annotation.validator(),
                annotation.preprocessor(),
                annotation.postprocessor());

        String topic = "";
        Object topicHeader = message.getHeaders().get(KafkaHeaders.TOPIC);
        if (topicHeader instanceof byte[] bts) {
            topic = new String(bts, StandardCharsets.UTF_8);
        }
        else if (topicHeader instanceof String t) {
            topic = t;
        }

        return new KafkaProducerModel<>(message,
                annotation.validator(),
                annotation.preprocessor(),
                annotation.postprocessor(),
                topic);
    }

    private Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
