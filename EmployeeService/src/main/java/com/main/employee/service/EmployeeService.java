package com.main.employee.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.employee.models.Employee;
import com.main.employee.models.UserPrincipal;
import com.main.employee.repository.EmployeeRepository;
import com.main.employee.utils.SecurityUtil;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository emprepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authmngr;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	SecurityUtil secUtil;
	
	public String validateUser(Employee emp)
	{
		if(emp.getEmail()==null || "".equalsIgnoreCase(emp.getEmail()))
			return "Email Id is required";
		if(emp.getPassword()==null || "".equalsIgnoreCase(emp.getPassword()))
			return "Password is required";
		Authentication auth = authmngr.authenticate(new UsernamePasswordAuthenticationToken(emp.getEmail(), emp.getPassword()));
		if(auth.isAuthenticated())
		{
			UserPrincipal e = (UserPrincipal) auth.getPrincipal();
			Employee empDetails = e.getEmpDetails();
			String role = auth.getAuthorities()
			        .stream()
			        .findFirst()
			        .get()
			        .getAuthority();
			return jwtService.generateToken(e.getUsername(),role,empDetails.getEmployeeId(),empDetails.getName());
		}
		return "Invalid Username or Password";
	}
	
	public Employee getEmployee()
	{
		try
		{
			return secUtil.getCurrentUser().getEmpDetails();
		}
		catch(Exception e)
		{
			System.out.println("Error fetching employee details"+e);
		}
		return null;
	}
	public Employee getManagerById()
	{
		Employee emp = null;
		try
		{
			emp = getEmployee();
			long managerId = Long.valueOf(emp.getManagerId());
			emp = emprepo.findById(managerId).get();
		}
		catch(Exception e)
		{
			System.out.println("Error fetching manager details"+e);
		}
		return emp;
	}
}
