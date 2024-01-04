package com.demo.kafka.framework;

import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;

import java.util.concurrent.CompletableFuture;

@Producer
public interface ProducerClient2 {

    @ProducerHandler(
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "avroPersonInfoResponseHandler"
    )
    CompletableFuture<SendResult<String, AvroPersonInfo>> sendAvroMessage(Message<AvroPersonInfo> message);

}
