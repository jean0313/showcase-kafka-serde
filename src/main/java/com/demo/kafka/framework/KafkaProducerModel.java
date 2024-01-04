package com.demo.kafka.framework;

import lombok.Data;
import org.springframework.messaging.Message;

@Data
public class KafkaProducerModel<T> extends KafkaProcessingModel {
    private Message<T> message;

    private String preprocessor;

    private String postprocessor;

    private String topic;

    public KafkaProducerModel() {

    }

    public KafkaProducerModel(Message<T> message, String validator, String preprocessor, String postprocessor, String topic) {
        this.message = message;
        this.preprocessor = preprocessor;
        this.postprocessor = postprocessor;
        this.topic = topic;
        setValidator(validator);
    }
}
