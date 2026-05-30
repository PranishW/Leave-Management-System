package com.main.leave.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
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

	public String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	public Long extractEmpId(String token) {
		return extractClaim(token, claims -> claims.get("empId", Long.class));
	}
	
	public String extractName(String token) {
		return extractClaim(token, claims -> claims.get("name", String.class));
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token) {
		try {
			extractAllClaims(token);
			return !isTokenExpired(token);
		} catch (Exception e) {
			return false;
		}
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

}
