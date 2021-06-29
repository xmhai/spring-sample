package com.cl.smart.flow.service;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;

public class ActivitiCallbackService implements JavaDelegate {
	RestTemplate restTemplate = new RestTemplate(); 
	
	@Getter @Setter
	private Expression url;
	
	@Getter @Setter
	private Expression method;
	
	@Getter @Setter
	private Expression body;
	
	@Override
	public void execute(DelegateExecution execution) {
		String p_url = (String) url.getValue(execution);
		String p_method = (String) method.getValue(execution);
		String p_body = (String) body.getValue(execution);
		
		HttpMethod httpMethod = HttpMethod.POST;
		if ("get".equals(p_method))
			httpMethod = HttpMethod.GET;
		else if ("post".equals(p_method))
			httpMethod = HttpMethod.POST;
		else if ("put".equals(p_method))
			httpMethod = HttpMethod.PUT;

		HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        HttpEntity<String> request= new HttpEntity<String>(p_body, headers);		
		restTemplate.exchange(p_url, httpMethod, request, String.class);
	}
}
