package com.example.webapp.service;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.entity.UserAccount;
import com.example.webapp.repo.UserAccountRepo;
import com.example.webapp.util.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserAccountRepo userRepo;
  private final Utils utils;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(UserAccountRepo userRepo, Utils utils, BCryptPasswordEncoder passwordEncoder) {
    this.userRepo = userRepo;
    this.utils = utils;
    this.passwordEncoder = passwordEncoder;
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
}
