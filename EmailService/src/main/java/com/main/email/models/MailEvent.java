package com.main.email.models;


public class MailEvent {
	private String msg;
	private String status;
	private String emailId;
	private long leaveReqId;
	private String startDate;
	private String endDate;
	private int leaveDays;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public long getLeaveReqId() {
		return leaveReqId;
	}
	public void setLeaveReqId(long leaveReqId) {
		this.leaveReqId = leaveReqId;
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
}
