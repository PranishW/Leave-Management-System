package com.main.leave.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.main.leave.Repo.LeaveBalanceRepository;
import com.main.leave.Repo.LeaveRequestsRepository;
import com.main.leave.models.LeaveBalance;
import com.main.leave.models.LeaveRequest;
import com.main.leave.models.MailEvent;

@Service
public class EmployeeLeaveRequestService {
	@Autowired
	LeaveRequestsRepository leavereqRepo;
	
	@Autowired
	LeaveBalanceRepository leavebalRepo;
	
	@Value("${spring.kafka.topic.name}")
	private String kafkaTopic;
	
	private final KafkaTemplate<String, MailEvent> kafkatemplate;
	
	public EmployeeLeaveRequestService(KafkaTemplate<String, MailEvent> kafkatemplate)
	{
		this.kafkatemplate = kafkatemplate;
	}
	
	public String initiateLeaveRequest(LeaveRequest leaveReq,String email)
	{
		String msg="";
		try
		{
			LeaveBalance lb = getLeaveBalance(leaveReq.getEmployeeId());
			if(lb.getRemaining()>=leaveReq.getLeaveDays())
			{
				leaveReq.setStatus("Pending");
				lb.setRemaining(lb.getRemaining()-leaveReq.getLeaveDays());
				lb.setUsed(lb.getUsed()+leaveReq.getLeaveDays());
				leaveReq = leavereqRepo.save(leaveReq);
				leavebalRepo.save(lb);
				msg = "Leave request initiated, Pending for approval with your manager. Leave Request Id - "+leaveReq.getLeaveRequestId();
				MailEvent mail = new MailEvent();
				mail.setEmailId(email);
				mail.setMsg("Leave request initiated, Pending for approval with your manager. Leave Request Id - "+leaveReq.getLeaveRequestId());
				mail.setStatus("Pending");
				mail.setLeaveReqId(leaveReq.getLeaveRequestId());
				sendEmailMsg(mail);
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
	
	public void sendEmailMsg(MailEvent mail)
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
}
