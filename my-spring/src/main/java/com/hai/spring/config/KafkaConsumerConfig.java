package com.hai.spring.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.listener.DefaultErrorHandler;

import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	private Long interval = 1000L; // 1s

	private Long maxAttempts = 3L;

	private static int messageReceived = 0;
	
	@Bean
	public DefaultErrorHandler errorHandler() {
	    BackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
	    DefaultErrorHandler errorHandler = new DefaultErrorHandler((consumerRecord, exception) -> {
	        // logic to execute when all the retry attemps are exhausted
	    	System.out.println("*** All retry attemps are failed ***");
	    }, fixedBackOff);
	    return errorHandler;
	}
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.86.43:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setCommonErrorHandler(errorHandler());
		factory.setConcurrency(1);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
	    factory.afterPropertiesSet();
		return factory;
	}

	@KafkaListener(topics = "my-topic", groupId = "foo")
	public void listen(@Payload String message, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws Exception {
		messageReceived++;
		Thread t = Thread.currentThread();
		System.out.println("Total number of message received: " + messageReceived);
	    System.out.println("Thread: " + t.getId() + "|" + t.getName()
	    		+ "; Instance ID: " + System.identityHashCode(this)
	    		+ "; From partition: " + partition
	    		+ "; Received Message: " + message);
	    Thread.sleep(3000);
	    
	    if (messageReceived>=3) {
		    throw new Exception("Error thrown when processing the message");
	    }
	    
	    ack.acknowledge();
	}
}
