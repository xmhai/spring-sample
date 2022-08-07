package com.example.aws.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class SqsQueueSender {
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void send(String message) {
        queueMessagingTemplate.send("MyQueue", MessageBuilder.withPayload(message).build());
        System.out.println("Message sent successfully: "+message);
    }
}