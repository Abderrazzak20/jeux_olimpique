package com.example.jeux_olimpique.controller;


import com.example.jeux_olimpique.DTO.AuthRequest;
import com.example.jeux_olimpique.DTO.AuthResponse;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
  private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
     return authService.login(request);
    }
    
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
    	User createUser = authService.createUser(user);
        return ResponseEntity.ok(createUser);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("Invalid credentials")) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
       
        if (ex.getMessage().contains("User already exists")) {
            return ResponseEntity.status(409).body(ex.getMessage());
        }
        return ResponseEntity.internalServerError().body("Errore del server");
    }

}
