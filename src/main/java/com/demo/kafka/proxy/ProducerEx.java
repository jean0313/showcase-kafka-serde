package com.demo.kafka.proxy;

public interface ProducerEx {
    <V> void send(String topic, V message);
}
