package com.main.vo;

public class EmployeeDTO {
	private String emailId;
	private String name;
	
	public EmployeeDTO(String emailId, String name) {
		super();
		this.emailId = emailId;
		this.name = name;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
