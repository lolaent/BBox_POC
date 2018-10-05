package com.lolatech.springkafka.springkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableCaching
public class SpringkafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringkafkaApplication.class, args);
	}
}
