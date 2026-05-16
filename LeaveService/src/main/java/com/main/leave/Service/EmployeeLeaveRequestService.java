package com.main.leave.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.leave.Repo.LeaveBalanceRepository;
import com.main.leave.Repo.LeaveRequestsRepository;
import com.main.leave.models.LeaveBalance;
import com.main.leave.models.LeaveRequest;

@Service
public class EmployeeLeaveRequestService {
	@Autowired
	LeaveRequestsRepository leavereqRepo;
	
	@Autowired
	LeaveBalanceRepository leavebalRepo;
	
	public String initiateLeaveRequest(LeaveRequest leaveReq)
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
				leavereqRepo.save(leaveReq);
				leavebalRepo.save(lb);
				msg = "Leave request initiated, Pending for approval with your manager";
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
}
