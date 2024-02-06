package com.example.webapp.service;

import com.example.webapp.entity.User;
import com.example.webapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User createUser(String email, String password, String firstName, String lastName) {
        // Implement logic for creating a new user, including password hashing with BCrypt
    }

    public User updateUser(Long userId, String firstName, String lastName, String password) {
        // Implement logic for updating user information
    }

    public User getUser(Long userId) {
        // Implement logic for getting user information
    }
}
