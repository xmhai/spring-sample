package com.example.swagger;

import javax.validation.constraints.NotNull;

public class Address {
	@NotNull
	private String postalCode;
	private String block;
	private String unitNo;
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
}
