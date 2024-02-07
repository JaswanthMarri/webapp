package com.example.webapp.repo;

import com.example.webapp.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

//@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UserAccount> {
    UserAccount findByUsername(String email);
}

