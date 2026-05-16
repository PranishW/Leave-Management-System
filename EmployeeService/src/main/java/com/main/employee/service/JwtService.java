package com.main.employee.service;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String SECRET_KEY;
	private SecretKey key;
	
	public JwtService()
	{
		SECRET_KEY = generateSecretKey();
		key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	public String generateSecretKey() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGen.generateKey();
			System.out.println("Secret Key : " + secretKey.toString());
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error generating secret key", e);
		}
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>(); // Claims can include custom data (e.g., roles, permissions)}
		claims.put("username", username); // adding custom claim
		return Jwts.builder().setClaims(claims) // Add claims to the token
				.setSubject(username) // Set the subject (e.g., the username)
				.setIssuedAt(new Date(System.currentTimeMillis())) // Current time as issue time
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // Token expiration time (3
																							// hours)
				.signWith(key) // Sign the token with the secret key
				.compact(); // Generate the token
	}
}
