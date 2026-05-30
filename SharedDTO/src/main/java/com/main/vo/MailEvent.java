package com.main.vo;

public class MailEvent {

	private String msg;
	private String status;
	private String emailId;
	private long leaveReqId;
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

}
