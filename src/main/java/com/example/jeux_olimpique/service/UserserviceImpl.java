package com.example.jeux_olimpique.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jeux_olimpique.models.User;
import com.example.jeux_olimpique.repository.UserRepository;
import com.example.jeux_olimpique.utils.Utilss;
@Service

public class UserserviceImpl implements UserService {

	@Autowired
    private  UserRepository userRepository;
	
	@Autowired Utilss utils;
	
	@Override
	public User createUser(User user) {
		 if (userRepository.findByEmail(user.getEmail()).isPresent()) {
	            throw new RuntimeException("Email già in uso");
	        }
		 String newKey = utils.generateKey();
		 user.setAccountKey(newKey);
	        return userRepository.save(user);
	    }
	@Override
	public User getUserById(Long id) {
		
		return userRepository.findById(id)
		.orElseThrow(()-> new RuntimeException("utente non esiste"));
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
		userRepository.deleteById(id);
		
	}
}
