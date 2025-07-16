package com.example.jeux_olimpique.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SecurityConfig {

	private final JwtFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;
	
	public SecurityConfig(JwtFilter jwtFilter ,CustomUserDetailsService userDetailsService ) {
		this.jwtFilter=jwtFilter;
		this.userDetailsService=userDetailsService;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		return http
				.csrf(csrf->csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**").permitAll()
						.anyRequest().authenticated()
)
				
				.authenticationProvider(AuthenticationProvider())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	} 
}
