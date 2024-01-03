package com.demo.kafka.controller;

import com.demo.kafka.framework.ProducerClient;
import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import com.demo.kafka.services.KafkaProducerClient;
import com.demo.kafka.services.ProducerClient2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaProducerController {

    @Autowired
    private ProducerClient producerClient;

    @GetMapping("/m/json/{name}/{age}")
    public void sendMJson(@PathVariable("name") String name, @PathVariable("age") int age) throws ExecutionException, InterruptedException {
        CompletableFuture<SendResult<String, PersonInfo>> future = producerClient.sendDemoMessage(new PersonInfo(name, age));
        System.out.println(future.whenComplete(((stringPersonInfoSendResult, throwable) -> {

        })));
    }

    @GetMapping("/a/json/{name}/{age}")
    public void sendAJson(@PathVariable("name") String name, @PathVariable("age") int age) {
//        producerClient.sendAvroMessage(AvroPersonInfo.newBuilder().setName(name).setAge(age).build());
    }


}
