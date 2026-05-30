package com.main.employee.models;

import java.util.Collection;
import java.util.Collections;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Employee emp;
	public UserPrincipal(Employee emp)
	{
		this.emp=emp;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Collections.singleton(new SimpleGrantedAuthority(emp.getRole()));
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return emp.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return emp.getEmail();
	}
	public Employee getEmpDetails()
	{
		return emp;
	}
}
