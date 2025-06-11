package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.User;

public interface UserService {
	public User createUser(User user);

	public Optional<User> getUserById(Long id);

	public List<User> getAllUsers();

	public User updateUser(User user);

	public void deleteUser(Long id);
}
