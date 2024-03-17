package com.example.webapp.controller;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.dto.UserUpdateDTO;
import com.example.webapp.service.UserService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
@Slf4j
public class WebAppController {

    @Autowired
    private UserService userService;

    @GetMapping("/healthz")
    public ResponseEntity<Void> dbHealthCheck() {
        return userService.HealthCheckService();
    }
    @PostMapping("/v1/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserDTO createUserRequest) {
        // Implement logic to handle user registration
        UserResponse createdUser = null;
        try{
        createdUser = userService.createUser(createUserRequest);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/v1/user")
    @PutMapping({"/v1/user","/healthz"})
    @PostMapping({"/v1/user/self","/healthz"})
    @DeleteMapping({"/v1/user","/v1/user/self","/healthz"})
    @PatchMapping({"/v1/user","/v1/user/self","/healthz"})
    public ResponseEntity<Void> handleUnsupportedMethods() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).headers(userService.noCacheHeaders()).build();
    }

    @PutMapping("/v1/user/self")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserUpdateDTO updateUserRequest) {
        if(!userService.updateUser(updateUserRequest)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/user/self")
    public ResponseEntity<UserResponse> getUser() {
        UserResponse user =userService.getUser();
        return new ResponseEntity<UserResponse>( user,HttpStatus.OK);
    }
}

