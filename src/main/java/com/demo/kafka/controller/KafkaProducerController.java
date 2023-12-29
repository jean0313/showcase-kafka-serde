package com.demo.kafka.controller;

import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import com.demo.kafka.proxy.Producer;
import com.demo.kafka.services.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController implements CommandLineRunner {

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

    @Autowired
    Producer p;

    @Override
    public void run(String... args) throws Exception {
        p.send("demo", new PersonInfo("anna", 18));
        p.send("avro-topic", AvroPersonInfo.newBuilder().setName("harry potter").setAge(18).build());
    }
}
