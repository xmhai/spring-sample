package com.example.aws.sqs;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import org.springframework.beans.factory.annotation.Autowired;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class SqsQueueSender {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SqsQueueSender(AmazonSQSAsync amazonSQSAsync) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSQSAsync);
    }

    public void send(String message) {
        this.queueMessagingTemplate.send("MyQueue", MessageBuilder.withPayload(message).build());
        System.out.println("Message sent successfully: "+message);
    }
}