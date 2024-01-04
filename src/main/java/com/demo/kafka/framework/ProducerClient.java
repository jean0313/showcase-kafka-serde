package com.demo.kafka.framework;

import com.demo.kafka.model.PersonInfo;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Producer
public interface ProducerClient {

    @ProducerHandler(
            topic = "demo",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "kafkaResponseHandler"
    )
    CompletableFuture<SendResult<String, PersonInfo>> sendDemoMessage(Message<PersonInfo> message);

    @ProducerHandler(
            topic = "demo",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "kafkaResponseHandler"
    )
    SendResult<String, PersonInfo> sendDemoMessage(Message<PersonInfo> message, Duration timeout);
}
