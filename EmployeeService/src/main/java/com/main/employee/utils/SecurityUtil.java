package com.main.employee.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.main.employee.models.UserPrincipal;

@Component
public class SecurityUtil {

    public UserPrincipal getCurrentUser() {

        return (UserPrincipal) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
