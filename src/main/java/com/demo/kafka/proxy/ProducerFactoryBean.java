package com.demo.kafka.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

public class ProducerFactoryBean implements FactoryBean<Producer>, ApplicationContextAware {
    private ApplicationContext applicationContext;

    public ProducerFactoryBean(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ProducerFactoryBean() {
    }

    @Override
    public Producer getObject() throws Exception {
        return (Producer) Proxy.newProxyInstance(Producer.class.getClassLoader(), new Class[]{Producer.class}, new ProducerInvocation(applicationContext));
    }

    @Override
    public Class<?> getObjectType() {
        return Producer.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
