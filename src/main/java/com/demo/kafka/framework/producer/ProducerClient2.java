package com.demo.kafka.framework.producer;

import com.demo.kafka.framework.Producer;
import com.demo.kafka.framework.ProducerHandler;
import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;

@Producer
public interface ProducerClient2 {

    @ProducerHandler(
            topic = "avro-topic",
            validator = "Validator",
            preprocessor = "Preprocessor",
            postprocessor = "avroPersonInfoResponseHandler"
    )
    ListenableFuture<SendResult<String, AvroPersonInfo>> sendAvroMessage(Message<AvroPersonInfo> message);

}
