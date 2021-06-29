package com.cl.smart.flow.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessDefinitionResponse {
	private String id;
	private String name;
	private String description;
	private int version;
	private Date deploymentTime;
}
