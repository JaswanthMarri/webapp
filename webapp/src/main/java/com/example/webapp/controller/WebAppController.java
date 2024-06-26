package com.example.webapp.controller;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.dto.UserUpdateDTO;
import com.example.webapp.service.UserService;
import com.example.webapp.util.Pubsub;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
// @Slf4j
public class WebAppController {

  @Autowired private UserService userService;
  private static final Logger LOGGER = LoggerFactory.getLogger(WebAppController.class);
  @Autowired private Pubsub pubsub;

  @Value("${pubsub.skip:true}")
  private boolean isExecuted;

  @GetMapping("/healthz")
  public ResponseEntity<Void> dbHealthCheck() {
    LOGGER.info("Hello, this is a structured log message!");
    return userService.HealthCheckService();
  }

  @PostMapping("/v5/user")
  public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserDTO createUserRequest) {
    // Implement logic to handle user registration
    UserResponse createdUser = null;
    try {
      createdUser = userService.createUser(createUserRequest);
      if (isExecuted) {
        pubsub.publishMessage(createdUser.getFirstName(), createdUser.getUsername());
      }
    } catch (Exception e) {
      LOGGER.info(e.getMessage());
      return ResponseEntity.badRequest().build();
    }
    return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
  }

  @GetMapping("/v5/user")
  @PutMapping({"/v5/user", "/healthz", "/v5/register"})
  @PostMapping({"/v5/user/self", "/healthz", "/v5/register"})
  @DeleteMapping({"/v5/user", "/v5/user/self", "/healthz", "/v5/register"})
  @PatchMapping({"/v5/user", "/v5/user/self", "/healthz", "/v5/register"})
  public ResponseEntity<Void> handleUnsupportedMethods() {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .headers(userService.noCacheHeaders())
        .build();
  }

  @PutMapping("/v5/user/self")
  public ResponseEntity<String> updateUser(@RequestBody @Valid UserUpdateDTO updateUserRequest) {
    if (!userService.hasVerified() && isExecuted) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    if (!userService.updateUser(updateUserRequest)) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/v5/user/self")
  public ResponseEntity<UserResponse> getUser() {
    if (!userService.hasVerified() && isExecuted) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    UserResponse user = userService.getUser();
    return new ResponseEntity<UserResponse>(user, HttpStatus.OK);
  }

  @GetMapping("/v5/register")
  public ResponseEntity verifyUser(@RequestParam("token") String token) {
    // Implement logic to handle user registration
    if (StringUtils.isBlank(token)) {
      LOGGER.info("Token is empty");
      return new ResponseEntity("Token is invalid", HttpStatus.FORBIDDEN);

    }
    return userService.verifyUser(token);
  }
}
