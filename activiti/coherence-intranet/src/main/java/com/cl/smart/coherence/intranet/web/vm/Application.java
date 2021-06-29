package com.cl.smart.coherence.intranet.web.vm;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Application {
	private String id;
	private String applicationNumber;
	private String applicationData;
	private String status;
	private String applicantIdNumber;
	private String applicantFirstName;
	private String applicantLastName;
	private String applicantEmail;
	private String createdBy;
	private Date createdDate;
	
	private String taskId;
}
