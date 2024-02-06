package com.example.webapp.repo;

import com.example.webapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Long> {
    // Additional custom queries can be added here if needed
}


