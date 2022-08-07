package com.example.aws.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
public class SqsController {
	@Autowired
	private SqsQueueSender sqsQueueSender;
	
	@PostMapping("/send")
	public void send(String message) {
		sqsQueueSender.send("Hello, world!");
	}
}
