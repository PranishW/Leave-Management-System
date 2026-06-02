package com.main.leave.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.leave.Service.LeaveRequestService;
import com.main.leave.models.LeaveBalance;
import com.main.leave.models.LeaveRequest;
import com.main.leave.utils.SecurityUtil;

@RestController
@RequestMapping("/emp")
public class EmployeeLeaveController {
	
	@Autowired
	LeaveRequestService empService;
	
	@Autowired
	SecurityUtil secUtil;
	
	@PostMapping("/initiateLeaveRequest")
	public @ResponseBody ResponseEntity<String> initiateLeave(@RequestBody LeaveRequest req)
	{
		try {
			long authId=secUtil.getLoggedinEmpId();
			if (authId != 0)
				req.setEmployeeId(authId);
			String msg = empService.initiateLeaveRequest(req,secUtil.getCurrentUsername(),secUtil.getEmployeeName());
			return ResponseEntity.ok(msg);
		} catch (Exception e) {
			System.out.println("in initiateLeave catch :- "+e);
			return ResponseEntity.internalServerError().body("Internal Server Error");
		}
	}
	
	@GetMapping("/showLeaveBalance/{empId}")
	public @ResponseBody ResponseEntity<LeaveBalance> showLeaveBalance(@PathVariable long empId)
	{
		try {
			long authId=secUtil.getLoggedinEmpId();
			if (authId == 0 || authId!=empId )
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			LeaveBalance leaveBal = empService.getLeaveBalance(empId);
			return ResponseEntity.ok(leaveBal);
		} catch (Exception e) {
			System.out.println("in showLeaveBalance catch :- "+e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@GetMapping("/showLeaveRequests/{empId}")
	public @ResponseBody ResponseEntity<List<LeaveRequest>> showLeaveRequests(@PathVariable long empId)
	{
		try {
			long authId=secUtil.getLoggedinEmpId();
			if (authId == 0 || authId!=empId )
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			List<LeaveRequest> leaveRequests = empService.getLeaveRequests(empId);
			if(leaveRequests.isEmpty())
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(leaveRequests);
		} catch (Exception e) {
			System.out.println("in showLeaveRequests catch :- "+e);
			return ResponseEntity.internalServerError().build();
		}
	}
}
