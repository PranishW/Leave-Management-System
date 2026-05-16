package com.main.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.employee.models.Employee;
import com.main.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository emprepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public String validateUser(Employee emp)
	{
		if(emp.getEmail()==null || "".equalsIgnoreCase(emp.getEmail()))
			return "Email Id is required";
		if(emp.getPassword()==null || "".equalsIgnoreCase(emp.getPassword()))
			return "Password is required";
		Employee employee = emprepo.findByEmail(emp.getEmail());
		if(employee==null || !passwordEncoder.matches(emp.getPassword(), employee.getPassword()))
			return "Invalid Email or password";
		
		return "User "+employee.getName()+" Logged in Successfully";
	}
}
