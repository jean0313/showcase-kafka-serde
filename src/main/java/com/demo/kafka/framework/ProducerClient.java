package com.demo.kafka.framework;

import com.demo.kafka.model.PersonInfo;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

@Producer
public interface ProducerClient {

    @ProducerHandler(
            topic = "demo",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "Postprocessor"
    )
    CompletableFuture<SendResult<String, PersonInfo>> sendDemoMessage(PersonInfo message);
}
