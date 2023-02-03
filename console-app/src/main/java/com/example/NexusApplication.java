package com.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NexusApplication {
	private String id;
	private String publicId;
	private String name;
	private String organizationId;
	private String contactUserName;
	private List<String> applicationTags;
}