package com.cl.smart.coherence.intranet.web.vm;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Task {
	private String taskId;
	private String processInstanceId;
	private String name;
	private String assignee;
	private Map<String, String> variables;
}
