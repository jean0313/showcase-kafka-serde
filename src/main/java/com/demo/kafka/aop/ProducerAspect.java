package com.demo.kafka.aop;

import com.demo.kafka.annotations.ProducerHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProducerAspect {
    private static final Logger logger = LoggerFactory.getLogger(ProducerAspect.class);

    @Around("@annotation(producerHandler)")
    public void aroundEnhance(ProceedingJoinPoint joinPoint, ProducerHandler producerHandler) throws Throwable {
        logger.info("log before method...");
        joinPoint.proceed();
        logger.info("log after method...");
    }
}
