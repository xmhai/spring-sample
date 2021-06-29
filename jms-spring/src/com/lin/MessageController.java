package com.lin;

import java.util.Date;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {
	
	@Autowired
	private QueueConnectionFactory connectionFactory;

	@Autowired
	private Queue testQueue;
	
    @GetMapping(path= "/send")
    @ResponseBody
    public String send(){
		try {
			QueueConnection connection = connectionFactory.createQueueConnection();
	        connection.start();
	        
	        QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
	        javax.jms.QueueSender queueSender = session.createSender(testQueue);
	        String messageText = "Message-"+(new Date().toString());
            TextMessage queueMessage = session.createTextMessage(messageText);
            queueSender.send(queueMessage);
			
	        session.close();
			connection.close();
			return "Message sent successfully, text: "+messageText;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
    }
}