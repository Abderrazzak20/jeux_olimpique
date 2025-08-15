package com.example.jeux_olimpique.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.UserRepository;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

	private final String SECRET_KEY = "xG7h!kVbWpTz@98A2sLq#R1fP0eD5uYjC6nM$ZlS4oQ3wXbVtKpH";

	private final long EXPIRATION = 1000 * 60 * 2; 

	private final UserRepository userRepository;

	public JwtService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Key getSignKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}

	public String generateToken(UserDetails userDetails) {
		User user = userRepository.findByEmail(userDetails.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

		return Jwts.builder().setSubject(userDetails.getUsername()) // di solito email
				.claim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
				.claim("id", user.getId()).setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("is_admin", user.isAdmin())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateTokenRegister(User user) {
		List<String> roles = new ArrayList<>();
		roles.add("roles");
		return Jwts.builder().setSubject(user.getEmail()).claim("roles", roles) // o usa user.getRoles() se li hai
				.claim("id", user.getId()).setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("is_admin", user.isAdmin())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean isTokenValid(String token, String email) {
		return extractEmail(token).equals(email) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		Date expiration = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody()
				.getExpiration();
		return expiration.before(new Date());
	}
}
