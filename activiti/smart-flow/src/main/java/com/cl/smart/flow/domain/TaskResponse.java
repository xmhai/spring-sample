package com.cl.smart.flow.domain;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TaskResponse {
	private String taskId;
	private String processInstanceId;
	private String name;
	private String assignee;
	private Map<String, String> variables;
}
