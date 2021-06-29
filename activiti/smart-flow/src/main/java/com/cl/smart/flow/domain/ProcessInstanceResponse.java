package com.cl.smart.flow.domain;

import java.util.Date;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessInstanceResponse {
	private String processInstanceId;
	private String processDefinitionKey;
	private Map<String, Object> variables;
	private Date startTime;
	private String startUserId;
	private boolean isEnded;
}
