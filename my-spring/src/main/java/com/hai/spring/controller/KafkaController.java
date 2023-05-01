package com.hai.spring.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
	private final String TOPIC = "my-topic";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@GetMapping("/kafka/send/{count}")
	public String send(@PathVariable Integer count) throws Exception {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSSSSSS");
		
		int numOfMessges = count==null ? 1 : count;
		
		for (int i=0; i<numOfMessges; i++) {
			UUID uuid = UUID.randomUUID();
			LocalDateTime now = LocalDateTime.now();
			String message = dtf.format(now) + "-" + uuid.toString();

			kafkaTemplate.send(TOPIC, message).get(3, TimeUnit.SECONDS);
			System.out.println("Sent message: "+message);
		}
		
		return "Message sent successfully";
	}
}
