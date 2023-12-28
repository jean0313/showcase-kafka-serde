package com.demo.kafka.controller;

import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import com.demo.kafka.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    @Value("${demo.topic:avro-topic}")
    private String topic;
    @Autowired
    private KafkaProducerService producer;

    @GetMapping("/avro/{name}/{age}")
    public void sendAVRO(@PathVariable("name") String name, @PathVariable("age") int age) {
        producer.sendMessage(topic, AvroPersonInfo.newBuilder().setName(name).setAge(age).build());
    }

    @GetMapping("/json/{name}/{age}")
    public void sendJson(@PathVariable("name") String name, @PathVariable("age") int age) {
        producer.sendMessage("demo", new PersonInfo(name, age));
    }


}
