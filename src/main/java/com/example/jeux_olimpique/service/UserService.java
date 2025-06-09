package com.example.jeux_olimpique.service;

import java.util.List;

import com.example.jeux_olimpique.models.User;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(Long id);
}
