package com.demo.kafka;

import com.demo.kafka.framework.Producer;
import com.demo.kafka.framework.ProducerScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@ProducerScan(value = "com.demo.kafka.framework.producer")
@SpringBootApplication
public class KafkaDemosApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemosApplication.class, args);
	}
}
