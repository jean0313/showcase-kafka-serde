package com.demo.kafka.proxy;

import com.demo.kafka.services.KafkaProducerClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

public class KafkaProducerClientFactoryBean implements FactoryBean<KafkaProducerClient>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public KafkaProducerClient getObject() throws Exception {
        return (KafkaProducerClient) Proxy.newProxyInstance(
                KafkaProducerClient.class.getClassLoader(),
                new Class[]{KafkaProducerClient.class},
                new KafkaProducerClientInvocation(applicationContext));
    }

    @Override
    public Class<?> getObjectType() {
        return KafkaProducerClient.class;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
