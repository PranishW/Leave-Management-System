package com.main.leave.models;


import jakarta.persistence.Entity;

import jakarta.persistence.Id;


@Entity(name = "leaveBal")
public class LeaveBalance {
	@Id
	private int employeeId;
	private Long allocated;
	private Long used;
	private Long remaining;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public Long getAllocated() {
		return allocated;
	}
	public void setAllocated(Long allocated) {
		this.allocated = allocated;
	}
	public Long getUsed() {
		return used;
	}
	public void setUsed(Long used) {
		this.used = used;
	}
	public Long getRemaining() {
		return remaining;
	}
	public void setRemaining(Long remaining) {
		this.remaining = remaining;
	}
}
