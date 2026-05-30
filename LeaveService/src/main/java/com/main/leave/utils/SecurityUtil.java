package com.main.leave.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
	
    private Authentication getAuthentication() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }
	@SuppressWarnings("unchecked")
	private Pair<Long, String> empDetails() {
		return (Pair<Long,String>) getAuthentication().getDetails();
	}
    public String getCurrentUsername() {
        return getAuthentication().getName();
    }

    public String getCurrentRole() {

        return getAuthentication().getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();
    }
    
    public Long getLoggedinEmpId()
    {
    	return empDetails().getKey();
    }
    public String getEmployeeName()
    {
    	return empDetails().getValue();
    }
}