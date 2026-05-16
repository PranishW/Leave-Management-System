package com.main.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/hello")
	public @ResponseBody ResponseEntity<String> hello()
	{
		return ResponseEntity.ok("Hello");
	}
}
