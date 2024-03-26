package com.example.webapp.repo;

import com.example.webapp.entity.UserAccount;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {
  UserAccount findByUsername(String email);

  UserAccount findByToken(String tkn);
}
