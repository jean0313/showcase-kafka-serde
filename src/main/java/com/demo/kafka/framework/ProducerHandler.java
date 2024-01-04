package com.demo.kafka.framework;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProducerHandler {

    String topic() default "";

    String validator() default "";

    String preprocessor() default "";

    String postprocessor() default "";
}
