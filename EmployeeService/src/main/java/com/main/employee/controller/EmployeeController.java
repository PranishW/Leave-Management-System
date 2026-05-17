package com.main.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.employee.models.Employee;
import com.main.employee.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	EmployeeService empservice;
	
	@PostMapping("/login")
	public @ResponseBody ResponseEntity<String> loginUser(@RequestBody Employee emp)
	{
		String msg = empservice.validateUser(emp);
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/getEmpDetails/{empId}")
	public @ResponseBody ResponseEntity<Employee> hello(@PathVariable long empId,Authentication authentication)
	{
		Employee emp = empservice.getEmployee(empId,authentication);
		if(emp==null)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		return ResponseEntity.ok(emp);
	}
}
