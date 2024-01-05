package com.demo.kafka.controller;

import com.demo.kafka.framework.ValidatorConfig;
import com.demo.kafka.framework.producer.ProducerClient;
import com.demo.kafka.model.PersonInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaProducerController implements ApplicationContextAware {

    @Autowired
    private ProducerClient producerClient;

//    @Autowired
//    private ProducerClient2 producerClient2;


    @Autowired
    private ValidatorConfig config;

    @GetMapping("/json/{name}/{age}")
    public void sendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        ListenableFuture<SendResult<String, PersonInfo>> future = producerClient.sendDemoMessage(
                MessageBuilder.withPayload(new PersonInfo(name, age))
                        .setHeader(KafkaHeaders.TOPIC, "demo").build());
        System.out.println(future.get());

        ValidatorConfig validatorConfig = applicationContext.getBean(ValidatorConfig.class);
        System.out.println("config:" + validatorConfig.getConfig());

    }

    @GetMapping("/bjson/{name}/{age}")
    public void blockSendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        SendResult<String, PersonInfo> ret = producerClient.sendDemoMessage(
                MessageBuilder.withPayload(new PersonInfo(name, age))
                        .setHeader(KafkaHeaders.TOPIC, "demo").build(), Duration.ofSeconds(10));
        System.out.println(ret.getProducerRecord());

//        ValidatorConfig validatorConfig = applicationContext.getBean(ValidatorConfig.class);
//        System.out.println("config:" + validatorConfig.getConfig());

    }

//    @GetMapping("/avro/{name}/{age}")
//    public void sendAJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
//        CompletableFuture<SendResult<String, AvroPersonInfo>> ret = producerClient2.sendAvroMessage(
//                MessageBuilder.withPayload(AvroPersonInfo.newBuilder().setName(name).setAge(age).build())
//                        .setHeader(KafkaHeaders.TOPIC, "avro-topic").build());
//        System.out.println(ret.get());
//    }


    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
