package com.example.aws.sqs;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Component;

@Component
public class SqsQueueListener {
	@MessageMapping("MyQueue")
	private void receiveMessage(String message, @Header("SenderId") String senderId) {
	    System.out.println("Message Received: "+message+ ", from: "+senderId);
	}
}
