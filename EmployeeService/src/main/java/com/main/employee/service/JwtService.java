package com.main.employee.service;

import java.nio.charset.StandardCharsets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;

@Service
public class JwtService {

	private String SECRET_KEY;
	private SecretKey key;

	public JwtService() {
		SECRET_KEY = "myVeryStrongSecretKey12345678900";
		key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username,String role,Long empId,String name) {
		Map<String, Object> claims = new HashMap<>(); // Claims can include custom data (e.g., roles, permissions)}
		// adding custom claim
		claims.put("role",role);
		claims.put("empId", empId);
		claims.put("name",name);
		return Jwts.builder().setClaims(claims) // Add claims to the token
				.setSubject(username) // Set the subject (e.g., the username)
				.setIssuedAt(new Date(System.currentTimeMillis())) // Current time as issue time
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // Token expiration time (3
																							// hours)
				.signWith(key) // Sign the token with the secret key
				.compact(); // Generate the token
	}

	public String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		// TODO Auto-generated method stub
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}
