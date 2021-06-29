package com.cl.smart.flow.domain;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TaskRequest {
	private String action;
	private String taskId;
	private String userId;
	private String groupId;
	private Map<String, Object> variables;
}
