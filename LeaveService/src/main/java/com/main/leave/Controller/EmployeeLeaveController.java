package com.main.leave.Controller;

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

import com.main.leave.Service.EmployeeLeaveRequestService;
import com.main.leave.models.LeaveBalance;
import com.main.leave.models.LeaveRequest;
import com.main.leave.utils.SecurityUtil;

@RestController
@RequestMapping("/emp")
public class EmployeeLeaveController {
	
	@Autowired
	EmployeeLeaveRequestService empService;
	
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
			String role = secUtil.getCurrentRole();
			if (authId == 0 || (authId!=empId && !"Manager".equalsIgnoreCase(role)))
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			LeaveBalance leaveBal = empService.getLeaveBalance(empId);
			return ResponseEntity.ok(leaveBal);
		} catch (Exception e) {
			System.out.println("in showLeaveBalance catch :- "+e);
			return ResponseEntity.internalServerError().build();
		}
	}
}
