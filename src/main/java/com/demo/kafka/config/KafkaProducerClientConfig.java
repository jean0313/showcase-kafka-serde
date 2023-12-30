package com.demo.kafka.config;

import com.demo.kafka.proxy.KafkaProducerClientImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(KafkaProducerClientImportBeanDefinitionRegistrar.class)
@Configuration
public class KafkaProducerClientConfig {
}
