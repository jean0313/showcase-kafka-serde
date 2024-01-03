package com.demo.kafka.framework;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

public class ProducerScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {
    private String basePackage;

    private Class<? extends Annotation> annotationClass;

    private Class<?> markerInterface;

    private Class<? extends ProducerProxyFactory> factoryBeanClass;

    private boolean processPropertyPlaceHolders;

    private ApplicationContext applicationContext;

    private String beanName;

    public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
        this.processPropertyPlaceHolders = processPropertyPlaceHolders;
    }

    public void setFactoryBeanClass(Class<? extends ProducerProxyFactory> factoryBeanClass) {
        this.factoryBeanClass = factoryBeanClass;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }
    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> superClass) {
        this.markerInterface = superClass;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        ClassPathProducerScanner scanner = new ClassPathProducerScanner(registry);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setResourceLoader(this.applicationContext);
        scanner.setProxyClassBean(ProducerProxyFactory.class);
        scanner.registerFilters();
        scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
