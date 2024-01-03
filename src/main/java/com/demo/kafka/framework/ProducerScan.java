package com.demo.kafka.framework;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(ProducerScannerRegistrar.class)
public @interface ProducerScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;

    Class<? extends ProducerProxyFactory> factoryBeanClass() default ProducerProxyFactory.class;

    String lazyInitialization() default "";

    String defaultScope() default AbstractBeanDefinition.SCOPE_DEFAULT;

    boolean processPropertyPlaceHolders() default true;
}
