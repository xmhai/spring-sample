package com.example.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.sqs.AmazonSQSAsync;

import io.awspring.cloud.messaging.core.QueueMessagingTemplate;

@Configuration
public class AwsConfig {
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
	    return new QueueMessagingTemplate(amazonSQSAsync);
	}
}
