package com.demo.kafka.services;

import com.demo.kafka.framework.ProducerHandler;
import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;



public interface KafkaProducerClient {

    @ProducerHandler(
            topic = "demo",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "Postprocessor"
    )
    void sendDemoMessage(PersonInfo message);

    @ProducerHandler(
            topic = "avro-topic",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "Postprocessor"
    )
    void sendAvroMessage(AvroPersonInfo message);
}
