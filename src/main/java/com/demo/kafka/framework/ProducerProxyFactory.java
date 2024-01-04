package com.demo.kafka.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Proxy;

public class ProducerProxyFactory<T> implements FactoryBean<T>, ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ProducerProxyFactory.class);

    private ApplicationContext applicationContext;

    private Class<?> interfaceClass;

    public ProducerProxyFactory() {

    }

    public ProducerProxyFactory(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public T getObject() throws Exception {
        System.out.println("ProducerProxyFactory: create proxy object...");
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, new ProducerInvocationHandler(applicationContext));
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
