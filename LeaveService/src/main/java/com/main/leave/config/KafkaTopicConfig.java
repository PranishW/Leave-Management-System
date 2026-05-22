package com.main.leave.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
	
	@Value("${spring.kafka.topic.name}")
	private String kafkaTopic;
	@Bean
	public NewTopic orderTopic()
	{
		System.out.println("Topic "+kafkaTopic+" creation method");
		return new NewTopic(kafkaTopic, 1,(short)1);
	}
}
