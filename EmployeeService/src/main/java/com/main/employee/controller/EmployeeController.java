package com.main.employee.controller;

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

import com.main.employee.models.Employee;
import com.main.employee.service.EmployeeService;
import com.main.vo.EmployeeDTO;
import com.main.vo.Manager;

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
	
	@GetMapping("/getLoggedInEmpDetails")
	public @ResponseBody ResponseEntity<Employee> fetchLoggedInEmpDetails()
	{
		Employee emp = empservice.getEmployee();
		if(emp==null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(emp);
	}
	
	@GetMapping("/getManagerDetails")
	public @ResponseBody ResponseEntity<Manager> getManagerDetails()
	{
		Manager emp = empservice.getManagerById();
		if(emp==null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(emp);
	}
	
	@GetMapping("/getEmployeeDetails/{empId}")
	public @ResponseBody ResponseEntity<EmployeeDTO> getEmployeeDetails(@PathVariable long empId)
	{
		EmployeeDTO empDetails = empservice.getEmployeeById(empId);
		if(empDetails==null)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(empDetails);
	}
	
	@GetMapping("/getMyTeam")
	public @ResponseBody ResponseEntity<List<Employee>> getMyTeam()
	{
		List<Employee> myTeam = empservice.getAllEmployees();
		if(myTeam==null || myTeam.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		return ResponseEntity.ok(myTeam);
	}
}
