package com.main.vo;

public class Manager {
	private String managerName;
	private long id;
	public Manager(String managerName, long id) {
		this.managerName = managerName;
		this.id = id;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
