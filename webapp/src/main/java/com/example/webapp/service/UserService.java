package com.example.webapp.service;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.entity.UserAccount;
import com.example.webapp.repo.UserAccountRepo;
import com.example.webapp.util.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService implements UserDetailsService, UserDetailsPasswordService {

  private final UserAccountRepo userRepo;
  private final Utils utils;
  private final BCryptPasswordEncoder passwordEncoder;
  private final DataSource ds;

  public UserService(
      UserAccountRepo userRepo, Utils utils, BCryptPasswordEncoder passwordEncoder, DataSource ds) {
    this.userRepo = userRepo;
    this.utils = utils;
    this.passwordEncoder = passwordEncoder;
    this.ds = ds;
  }

  public ResponseEntity<Void> HealthCheckService() {
    try (Connection conn = ds.getConnection()) {
      Statement stmt = conn.createStatement();
    } catch (SQLException ex) {
      log.info(ex.getMessage());
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
          .headers(noCacheHeaders())
          .build();
    }
    return ResponseEntity.status(HttpStatus.OK).headers(noCacheHeaders()).build();
  }

  public UserResponse createUser(UserDTO userReq) {

    UserAccount user = utils.convertUserDTOToEntity(userReq);

    if (userRepo.findByUsername(user.getUsername()) != null) { // .isPresent()) {
      throw new IllegalArgumentException(
          "User with email " + user.getUsername() + " already exists.");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    UserResponse resp = utils.convertUserEntityToDTO(userRepo.save(user));
    return resp;
  }

  public boolean updateUser(UserDTO userReq) {
    UserAccount user = userRepo.findByUsername(utils.getUserName());
    if (user != null
        && userReq.getUsername().toLowerCase().equals(user.getUsername().toLowerCase())) {
      //      UserAccount updatedUser = utils.convertUserDTOToEntity(userReq);
      user.setPassword(userReq.getPassword());
      user.setLastName(userReq.getLastName());
      user.setFirstName(userReq.getFirstName());
      userRepo.save(user);
      return true;
    }
    return false;
  }

  public UserResponse getUser() {
    return utils.convertUserEntityToDTO(userRepo.findByUsername(utils.getUserName()));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserAccount userAccount = userRepo.findByUsername(username);
    return userAccount;
  }

  public static HttpHeaders noCacheHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Pragma", "no-cache");
    return headers;
  }

  @Override
  public UserDetails updatePassword(UserDetails user, String newPassword) {
    UserAccount userAccount = userRepo.findByUsername(user.getUsername());
    return userAccount;
  }
}
