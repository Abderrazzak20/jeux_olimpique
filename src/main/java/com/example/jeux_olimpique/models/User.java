package com.example.jeux_olimpique.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

	@jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long UserId;
	private String username ;
	private String name;
	private String password;
	private String email;
	private String accountKey;

}
