package com.example.jeux_olimpique.Controller;

import com.example.jeux_olimpique.DTO.AuthRequest;
import com.example.jeux_olimpique.DTO.AuthResponse;
import com.example.jeux_olimpique.controller.AuthController;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.security.CustomUserDetailsService;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private JwtService jwtService;
	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void login_success() throws Exception {
		AuthRequest request = new AuthRequest("user@example.com", "password");
		AuthResponse response = new AuthResponse("fake-jwt");

		when(authService.login(any())).thenReturn(ResponseEntity.ok(response));

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("fake-jwt"));
	}

	@Test
	void register_success() throws Exception {
		User user = new User();
		user.setEmail("test@example.com");
		user.setPassword("pass");

		AuthResponse authResponse = new AuthResponse("jwt"); 

		when(authService.register(any())).thenReturn(authResponse);

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("jwt"));
	}

	@Test
	void login_invalidCredentials_returnsUnauthorized() throws Exception {
		AuthRequest request = new AuthRequest("wrong@example.com", "wrongpass");

		when(authService.login(any())).thenThrow(new RuntimeException("Invalid credentials"));

		mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isUnauthorized())
				.andExpect(content().string("Invalid credentials"));
	}

	@Test
	void register_existingUser_throwsException() throws Exception {
		User user = new User();
		user.setEmail("existing@example.com");

		when(authService.register(any())).thenThrow(new RuntimeException("User already exists"));

		mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user))).andExpect(status().isConflict())
				.andExpect(content().string("User already exists"));
	}

}
