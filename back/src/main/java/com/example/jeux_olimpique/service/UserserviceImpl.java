package com.example.jeux_olimpique.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.UserRepository;
import com.example.jeux_olimpique.utils.Utilss;

@Service

public class UserserviceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	Utilss utils;

	@Override
	public User createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email già in uso");
		}
		String newKey = utils.generateKey();
		user.setAccountKey(newKey);
		  String encodedPassword = passwordEncoder.encode(user.getPassword());
	        user.setPassword(encodedPassword);
		return userRepository.save(user);
	}

	@Override
	public Optional<User>  getUserById(Long id) {
		if(!userRepository.existsById(id)) {
			throw new RuntimeException("user pas trouve");
		}

		return userRepository.findById(id);
	}

	@Override
	public List<User> getAllUsers() {

		return userRepository.findAll();
	}

	@Override
	public User updateUser(User user) {
		if (!userRepository.existsById(user.getId())) {
			throw new RuntimeException("utente non esiste");
		}
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(Long id) {
		
		if(!userRepository.existsById(id)) {
			throw new RuntimeException("user not find");
		}
		userRepository.deleteById(id);

	}
}
