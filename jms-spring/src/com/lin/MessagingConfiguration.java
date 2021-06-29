package com.lin;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfiguration {

	@Bean
	public Context wso2mbContext() throws NamingException{
		System.out.println("Create Bean: wso2mbContext");
		return InitialContext.doLookup("java:global/remoteJMS");
	}; 
	
	@Bean
	public QueueConnectionFactory connectionFactory() throws NamingException{
		return (QueueConnectionFactory) wso2mbContext().lookup("qpidConnectionfactory");
	}

	@Bean
	public Queue testQueue() throws NamingException{
		System.out.println("Create Bean: connectionFactory");
		return (Queue) wso2mbContext().lookup("testQueue");
	}
}
