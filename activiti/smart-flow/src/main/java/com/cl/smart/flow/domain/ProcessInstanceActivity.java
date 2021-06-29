package com.cl.smart.flow.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ProcessInstanceActivity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
