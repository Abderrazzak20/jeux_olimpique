package com.example.jeux_olimpique.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jeux_olimpique.models.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional <User> findByEmail (String email);

}

