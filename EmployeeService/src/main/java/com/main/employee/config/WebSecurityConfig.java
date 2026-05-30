package com.main.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.main.employee.filter.JwtFilter;
import com.main.employee.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsServiceImpl userdetailservice;
	@Autowired 
	private JwtFilter jwtFilter;
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
    
        http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> 
        requests.requestMatchers("/employees/login").permitAll()
        .requestMatchers("/employees/getManagerDetails").hasAuthority("Employee")
        .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	
    @Bean
    public AuthenticationProvider authProvider()
    {
    	DaoAuthenticationProvider dao= new DaoAuthenticationProvider(userdetailservice);
    	dao.setPasswordEncoder(passwordEncoder());
    	
    	return dao;
    }
    
    @Bean
    public AuthenticationManager authmngr(AuthenticationConfiguration authconfig)
    {
    	return authconfig.getAuthenticationManager();
    }
}
