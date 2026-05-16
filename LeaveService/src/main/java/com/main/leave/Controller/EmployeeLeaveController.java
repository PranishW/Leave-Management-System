package com.main.leave.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/emp")
public class EmployeeLeaveController {
	
	@Autowired
	EmployeeLeaveRequestService empService;
	
	@PostMapping("/initiateLeaveRequest")
	public @ResponseBody ResponseEntity<String> initiateLeave(@RequestBody LeaveRequest req)
	{
		String msg = empService.initiateLeaveRequest(req);
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/showLeaveBalance/{empId}")
	public @ResponseBody ResponseEntity<LeaveBalance> showLeaveBalance(@PathVariable long empId)
	{
		LeaveBalance leaveBal = empService.getLeaveBalance(empId);
		return ResponseEntity.ok(leaveBal);
	}
}
