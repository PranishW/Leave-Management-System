package com.main.leave.Interfaces;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.main.leave.config.FeignConfig;
import com.main.vo.Manager;

@FeignClient(name = "emp-client", url = "${application.services.emp.url}",configuration = FeignConfig.class)
public interface EmployeeClient {
	
	@GetMapping("/getManagerDetails")
	public Manager getManagerDetails();
}
