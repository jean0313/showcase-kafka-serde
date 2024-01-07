package com.demo.kafka.controller;

import com.demo.kafka.framework.ValidatorConfig;
import com.demo.kafka.framework.producer.ProducerClient;
import com.demo.kafka.framework.producer.ProducerClient2;
import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import com.demo.kafka.validate.ValidateUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.Duration;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaProducerController implements ApplicationContextAware {

    @Autowired
    private ProducerClient producerClient;

    @Autowired
    private ProducerClient2 producerClient2;

    @Autowired
    private ValidatorConfig config;

    @GetMapping("/json/{name}/{age}")
    public void sendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, PersonInfo>> future = producerClient.sendDemoMessage(
                MessageBuilder.withPayload(new PersonInfo(name, age)).setHeader("txId", "12345").build());
        System.out.println(future.get());
    }

    @GetMapping("/bjson/{name}/{age}")
    public void blockSendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        SendResult<String, PersonInfo> ret = producerClient.sendDemoMessage(
                MessageBuilder.withPayload(new PersonInfo(name, age))
                        .setHeader(KafkaHeaders.TOPIC, "demo").build(), Duration.ofSeconds(10));
        System.out.println(ret.getProducerRecord());
    }

    @GetMapping("/avro/{name}/{age}")
    public void sendAvroJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, AvroPersonInfo>> future = producerClient2.sendAvroMessage(
                MessageBuilder.withPayload(AvroPersonInfo.newBuilder().setName(name).setAge(age).build()).setHeader("txId", "12345").build());
        System.out.println("avro:" + future.get());
    }

    @Bean
    LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

    @Autowired
    LocalValidatorFactoryBean validatorFactoryBean;

    @GetMapping("/validate")
    public void verify() {
        Validator validator = validatorFactoryBean.getValidator();
        ValidateUtils.Dog pop = new ValidateUtils.Dog("pop2", 12);
        Set<ConstraintViolation<ValidateUtils.Dog>> validate = validator.validate(pop);
        System.out.println(validate);
    }

    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
