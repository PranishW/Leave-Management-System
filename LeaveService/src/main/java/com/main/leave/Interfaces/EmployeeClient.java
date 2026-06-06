package com.main.leave.Interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.main.leave.config.FeignConfig;
import com.main.vo.EmployeeDTO;
import com.main.vo.Manager;

@FeignClient(name = "EMPLOYEESERVICE",configuration = FeignConfig.class)
public interface EmployeeClient {
	
	@GetMapping("/employees/getManagerDetails")
	public Manager getManagerDetails();
	
	@GetMapping("/employees/getEmployeeDetails/{empId}")
	public EmployeeDTO getEmployeeDetails(@PathVariable long empId);
}
