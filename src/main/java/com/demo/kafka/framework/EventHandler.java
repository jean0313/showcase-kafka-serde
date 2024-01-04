package com.demo.kafka.framework;

public class EventHandler<R extends KafkaProcessingModel> {


    public void execute(R request) {
        validate(request);
    }

    public void validate(R request) {

    }
}
