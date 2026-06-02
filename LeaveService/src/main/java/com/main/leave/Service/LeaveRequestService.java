package com.main.leave.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.main.leave.Interfaces.EmployeeClient;
import com.main.leave.Interfaces.LeaveBalanceRepository;
import com.main.leave.Interfaces.LeaveRequestsRepository;
import com.main.leave.models.LeaveBalance;
import com.main.leave.models.LeaveRequest;
import com.main.vo.MailEvent;
import com.main.vo.Manager;

@Service
public class LeaveRequestService {
	@Autowired
	LeaveRequestsRepository leavereqRepo;
	
	@Autowired
	LeaveBalanceRepository leavebalRepo;
	
	@Autowired
	EmployeeClient empClient;
	
	@Value("${spring.kafka.topic.name}")
	private String kafkaTopic;
	
	private final KafkaTemplate<String, MailEvent> kafkatemplate;
	
	public LeaveRequestService(KafkaTemplate<String, MailEvent> kafkatemplate)
	{
		this.kafkatemplate = kafkatemplate;
	}
	
	public String initiateLeaveRequest(LeaveRequest leaveReq,String email,String empName)
	{
		String emailMsg="";
		String msg="";
		Manager mn=null;
		try
		{
			LeaveBalance lb = getLeaveBalance(leaveReq.getEmployeeId());
			if(lb.getRemaining()>=leaveReq.getLeaveDays())
			{
				mn = empClient.getManagerDetails();
				leaveReq.setStatus("Pending");
				lb.setRemaining(lb.getRemaining()-leaveReq.getLeaveDays());
				lb.setUsed(lb.getUsed()+leaveReq.getLeaveDays());
				leaveReq = leavereqRepo.save(leaveReq);
				leavebalRepo.save(lb);
				emailMsg = "Dear "+empName+",\n\nLeave request initiated, pending for approval with your manager ( "+mn.getManagerName()+" , Employee Id - "+mn.getId()+" ) . Leave Request Id - "+leaveReq.getLeaveRequestId()+
				"\n\nLeave From : "+leaveReq.getStartDate()+"\nLeave Till : "+leaveReq.getEndDate()+"\nLeave Days : "+leaveReq.getLeaveDays()+
				"\n\nRegards,\nLeave Service LTD";
				MailEvent mail = new MailEvent();
				setEmailEvent(mail, leaveReq, email, emailMsg);
				sendEmailMsg(mail);
				msg = "Leave request Initiated Successfully. Leave Request Id - "+leaveReq.getLeaveRequestId();
			}
			else
				msg = "Not enough leaves";
		}
		catch(Exception e)
		{
			System.out.println("in initiateLeaveRequest catch :- "+e);
			msg = "Something went wrong.";
		}
		return msg;
	}
	
	public LeaveBalance getLeaveBalance(Long empId)
	{
		LeaveBalance lb=null;
		try
		{
			lb = leavebalRepo.findById(empId).get();
		}
		catch(Exception e)
		{
			System.out.println("in getLeaveBalance catch :- "+e);
		}
		return lb;
	}
	
	public void sendEmailMsg(com.main.vo.MailEvent mail)
	{
		kafkatemplate.send(kafkaTopic, mail)
        .whenComplete((result, ex) -> {

            if (ex == null) {

                System.out.println("Event published");

            } else {

            	System.out.println("Kafka publish failed"+ex);
            }
        });
	}
	
	public void setEmailEvent(MailEvent mail,LeaveRequest leaveReq,String email,String msg)
	{
		mail.setEmailId(email);
		mail.setMsg(msg);
		mail.setStatus(leaveReq.getStatus());
		mail.setLeaveReqId(leaveReq.getLeaveRequestId());
	}
	
	public List<LeaveRequest> getLeaveRequests(long empId)
	{
		return leavereqRepo.findByEmployeeId(empId);
	}
}
