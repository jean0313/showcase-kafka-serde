package com.demo.kafka.framework.impl;

import com.demo.kafka.framework.ResponseHandler;
import com.demo.kafka.model.AvroPersonInfo;
import com.demo.kafka.model.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component("avroPersonInfoResponseHandler")
public class AvroPersonInfoResponseHandler implements ResponseHandler<String, AvroPersonInfo> {
    private static final Logger logger = LoggerFactory.getLogger(AvroPersonInfoResponseHandler.class);

    @Override
    public void onSuccess(SendResult<String, AvroPersonInfo> result) {
        AvroPersonInfo value = result.getProducerRecord().value();
        logger.info("avro message sent success!!!!!!!:" + value.getName());
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.info("failed......");
    }
}
