package com.example.swagger;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserInfo {
	@NotNull(message = "username cannot be null")
	//@Schema(required = true)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	// if address is not null, it will be validated
	@Valid
	private Address address;
	
	// if phoneNumbers contains values, it will be validated
	private List<@NotBlank String> phoneNumbers;

	// use @Valid to validate array elements
	private List<@Valid Address> properties;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<Address> getProperties() {
		return properties;
	}

	public void setProperties(List<Address> properties) {
		this.properties = properties;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
