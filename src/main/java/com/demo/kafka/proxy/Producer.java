package com.demo.kafka.proxy;

public interface Producer {
    <V> void send(String topic, V message);
}
