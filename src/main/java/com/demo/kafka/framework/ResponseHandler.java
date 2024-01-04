package com.demo.kafka.framework;

import org.springframework.kafka.support.SendResult;

public interface ResponseHandler<K, V> {

    void onSuccess(SendResult<K, V> result);

    void onFailure(Throwable ex);
}
