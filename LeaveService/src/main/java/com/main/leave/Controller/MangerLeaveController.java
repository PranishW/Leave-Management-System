package com.main.leave.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.leave.Service.LeaveRequestService;
import com.main.leave.models.LeaveRequest;
import com.main.leave.models.LeaveStatus;
import com.main.leave.utils.SecurityUtil;

@RestController
@RequestMapping("/manager")
public class MangerLeaveController {
	
	@Autowired
	LeaveRequestService empService;
	
	@Autowired
	SecurityUtil secUtil;
	
	@GetMapping("/showLeaveRequestForManager")
	public @ResponseBody ResponseEntity<Page<LeaveRequest>> getLeaveRequestsForManager(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size)
	{
		try
		{
			long managerId = secUtil.getLoggedinEmpId();
			Page<LeaveRequest> leaveList = empService.getLeaveRequestsManager(managerId,page,size);
			if(leaveList.isEmpty())
				return ResponseEntity.noContent().build();
			return ResponseEntity.ok(leaveList);
		}
		catch(Exception e)
		{
			System.out.println("in getLeaveRequestsForManager catch :- "+e);
			return ResponseEntity.internalServerError().build();
		}
	}
	
	@PostMapping("/updateLeaveStatus/{leaveRequestId}")
	public @ResponseBody ResponseEntity<String> updateLeaveStatus(@PathVariable long leaveRequestId,@RequestBody LeaveStatus status)
	{
		try
		{
			long managerId = secUtil.getLoggedinEmpId();
			String managerName = secUtil.getEmployeeName();
			String msg = empService.updateLeaveStatus(leaveRequestId, managerId, status, managerName);
			if("Forbidden".equalsIgnoreCase(msg))
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			if("NO_CONTENT".equalsIgnoreCase(msg))
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Leave Request with mentioned Id is not present");
			return ResponseEntity.ok(msg);
		}
		catch(Exception e)
		{
			System.out.println("in getLeaveRequestsForManager catch :- "+e);
			return ResponseEntity.internalServerError().body("Internal Server Error");
		}
	}
}
