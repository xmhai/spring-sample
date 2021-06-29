package com.cl.smart.flow.domain;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessInstanceCreateRequest {
	private String processDefinitionKey;
	private Map<String, Object> variables;
}
