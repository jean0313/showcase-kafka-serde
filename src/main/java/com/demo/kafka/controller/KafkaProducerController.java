package com.demo.kafka.controller;

import com.demo.kafka.framework.ProducerClient;
import com.demo.kafka.framework.ValidatorConfig;
import com.demo.kafka.model.PersonInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaProducerController implements ApplicationContextAware {

    @Autowired
    private ProducerClient producerClient;


    @Autowired
    private ValidatorConfig config;

    @GetMapping("/m/json/{name}/{age}")
    public void sendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        CompletableFuture<SendResult<String, PersonInfo>> future = producerClient.sendDemoMessage(new PersonInfo(name, age));
        System.out.println(future.get());

        ValidatorConfig validatorConfig = applicationContext.getBean(ValidatorConfig.class);
        System.out.println("config:" + validatorConfig.getConfig());

    }

    @GetMapping("/a/json/{name}/{age}")
    public void sendAJson(@PathVariable("name") String name, @PathVariable("age") int age) {
//        producerClient.sendAvroMessage(AvroPersonInfo.newBuilder().setName(name).setAge(age).build());
    }


    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
