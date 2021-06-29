package com.cl.smart.coherence.application.domain;

import lombok.Data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Application {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Long id;
	private String applicationNumber;
	private String applicationData;
	private String status;
	private String applicantIdNumber;
	private String applicantFirstName;
	private String applicantLastName;
	private String applicantEmail;
	private String createdBy;
	private Date createdDate;
}
