package com.main.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.main.employee.models.Employee;
import com.main.employee.models.UserPrincipal;
import com.main.employee.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	EmployeeRepository emprepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee emp = emprepo.findByEmail(email);
		return new UserPrincipal(emp);
	}
	


}
