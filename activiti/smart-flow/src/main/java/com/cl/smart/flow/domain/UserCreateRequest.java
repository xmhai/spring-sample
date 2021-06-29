package com.cl.smart.flow.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCreateRequest {
	private String userId;
	private String lastName;
	private String firstName;
	private String email;
	private String groupId;
}
