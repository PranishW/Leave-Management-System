package com.main.leave.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "leaveRequests")
public class LeaveRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long leaveRequestId;
	private Long employeeId;	
	private String startDate;
	private String endDate;
	private int leaveDays;
	private String status;
	public Long getLeaveRequestId() {
		return leaveRequestId;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
