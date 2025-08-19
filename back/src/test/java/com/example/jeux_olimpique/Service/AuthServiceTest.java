package com.example.jeux_olimpique.Service;

import com.example.jeux_olimpique.DTO.AuthRequest;
import com.example.jeux_olimpique.DTO.AuthResponse;
import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.security.JwtService;
import com.example.jeux_olimpique.service.AuthService;
import com.example.jeux_olimpique.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AuthServiceTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtService jwtService;

	@Mock
	private UserService userService;

	@InjectMocks
	private AuthService authService;

	@Mock
	private Authentication authentication;

	@Mock
	private UserDetails userDetails;

	@Test
	void login_success() {
		AuthRequest request = new AuthRequest();
		request.setEmail("user@example.com");
		request.setPassword("password");

		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(jwtService.generateToken(userDetails)).thenReturn("jwt_token");

		ResponseEntity<AuthResponse> response = authService.login(request);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals("jwt_token", response.getBody().getToken());

		verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtService).generateToken(userDetails);
	}

	@Test
	void register_success() {
		User user = new User();
		user.setEmail("test@example.com");

		// mock del service che salva l'utente
		when(userService.createUser(user)).thenReturn(user);

		// mock del JWT
		when(jwtService.generateTokenRegister(user)).thenReturn("fake-jwt-token");

		// esegue il metodo da testare
		AuthResponse result = authService.register(user);

		// verifica il token restituito
		assertEquals("fake-jwt-token", result.getToken());

		// verifica che userService.createUser sia stato chiamato
		verify(userService).createUser(user);
		verify(jwtService).generateTokenRegister(user);
	}
}
