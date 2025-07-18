package com.example.jeux_olimpique.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.jeux_olimpique.DTO.AuthRequest;
import com.example.jeux_olimpique.DTO.AuthResponse;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.security.CustomUserDetailsService;
import com.example.jeux_olimpique.security.JwtService;

@Service
public class AuthService {
	  @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtService jwtService;
	    @Autowired
	    private UserService userService;
	
	
	    public ResponseEntity<AuthResponse> login( AuthRequest request) {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String token = jwtService.generateToken(userDetails);


	        return ResponseEntity.ok(new AuthResponse(token));
	    }
	    
	    public User createUser(@RequestBody User user) {
	        return userService.createUser(user);
	    }
}
