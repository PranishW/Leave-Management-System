package com.main.leave.config;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.main.leave.Service.JwtService;
import com.main.leave.utils.Pair;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtService jwtService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {final String requestTokenHeader = request.getHeader("Authorization");

	        String username = null;
	        String jwtToken = null;
	        String role=null;
	        String name=null;
	        long empId=0l;
	        Pair<Long,String> empDetails =null;
	        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	            jwtToken = requestTokenHeader.substring(7);
	            try {
	                username = jwtService.extractUserName(jwtToken);
	                role=jwtService.extractRole(jwtToken);
	                empId=jwtService.extractEmpId(jwtToken);
	                name=jwtService.extractName(jwtToken);
	            } catch (IllegalArgumentException e) {
	                System.out.println("Unable to get JWT Token");
	            } catch (ExpiredJwtException e) {
	                System.out.println("JWT Token has expired");
	            }
	        } else {
	            logger.warn("JWT Token does not begin with Bearer String");
	        }

	        if (jwtService.validateToken(jwtToken)) {
	        	List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                		username, null, authorities);
	                empDetails = new Pair<Long, String>(empId,name);
	                usernamePasswordAuthenticationToken.setDetails(empDetails);
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	        }
	        filterChain.doFilter(request, response);}

}
