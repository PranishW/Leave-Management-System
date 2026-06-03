package com.main.leave.Interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.main.leave.config.FeignConfig;
import com.main.vo.EmployeeDTO;
import com.main.vo.Manager;

@FeignClient(name = "emp-client", url = "${application.services.emp.url}",configuration = FeignConfig.class)
public interface EmployeeClient {
	
	@GetMapping("/getManagerDetails")
	public Manager getManagerDetails();
	
	@GetMapping("/getEmployeeDetails/{empId}")
	public EmployeeDTO getEmployeeDetails(@PathVariable long empId);
}
