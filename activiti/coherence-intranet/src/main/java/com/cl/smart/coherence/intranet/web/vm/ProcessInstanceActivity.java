package com.cl.smart.coherence.intranet.web.vm;

import java.util.Date;

import lombok.Data;

@Data
public class ProcessInstanceActivity {
	private String id;
	private String processInstanceId;
	private String taskId;
	private String name;
	private String description;
	private String action;
	private String actionUserId;
	private Date actionTime;
	private String variables;
}
