package com.lin;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
	@JmsListener(destination = "testQueue")
	public void receiveMessage(final TextMessage message) throws JMSException {
		System.out.println("Received message: "+message.getText());
	}
}
