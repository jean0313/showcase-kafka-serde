package com.demo.kafka.producer;

public interface Validator<T> {
    void validate(T data);
}
