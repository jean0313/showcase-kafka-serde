package com.demo.kafka.framework.impl;

import com.demo.kafka.framework.ResponseHandler;
import com.demo.kafka.model.PersonInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component("kafkaResponseHandler")
public class DefaultResponseHandler implements ResponseHandler<String, PersonInfo> {
    private static final Logger logger = LoggerFactory.getLogger(DefaultResponseHandler.class);

    @Override
    public void onSuccess(SendResult<String, PersonInfo> result) {
        PersonInfo value = result.getProducerRecord().value();
        logger.info("success!!!!!!!:" + value.getName());
    }

    @Override
    public void onFailure(Throwable ex) {
        logger.info("failed......");
    }
}
