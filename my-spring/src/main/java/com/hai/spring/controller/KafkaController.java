package com.hai.spring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
	private final String TOPIC = "test-topic";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/kafka/send")
	public String send() throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		String message = "Current time is "+dtf.format(now);
		kafkaTemplate.send(TOPIC, message).get(3, TimeUnit.SECONDS);
		return "Message sent successfully";
	}
}
