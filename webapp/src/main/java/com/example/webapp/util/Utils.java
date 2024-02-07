package com.example.webapp.util;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.entity.UserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Utils {

  public UserAccount convertUserDTOToEntity(UserDTO userReq) {
    UserAccount user = new UserAccount();
    user.setUsername(userReq.getUsername());
    user.setPassword(userReq.getPassword());
    user.setFirstName(userReq.getFirstName());
    user.setLastName(userReq.getLastName());
    return user;
  }

  public UserResponse convertUserEntityToDTO(UserAccount user) {
    UserResponse userResponse = new UserResponse();
    userResponse.setId(user.getId().toString());
    userResponse.setUsername(user.getUsername());
    userResponse.setLastName(user.getLastName());
    userResponse.setFirstName(user.getFirstName());
    userResponse.setAccountCreated(user.getAccountCreated());
    userResponse.setAccountUpdated(user.getAccountUpdated());
    return userResponse;
  }

  public String getUserName() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }


}
