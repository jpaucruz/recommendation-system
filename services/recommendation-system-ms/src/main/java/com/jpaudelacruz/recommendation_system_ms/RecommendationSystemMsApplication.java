package com.jpaudelacruz.recommendation_system_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class RecommendationSystemMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendationSystemMsApplication.class, args);
	}

}
