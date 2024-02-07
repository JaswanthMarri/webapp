package com.example.webapp.controller;

import com.example.webapp.dto.UserDTO;
import com.example.webapp.dto.UserResponse;
import com.example.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1")
public class WebAppController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDTO createUserRequest) {
        // Implement logic to handle user registration
        UserResponse createdUser = null;
        try{
        createdUser = userService.createUser(createUserRequest);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    @PutMapping("/user")
    @PostMapping("/user/self")
    @DeleteMapping({"/user","/user/self"})
    @PatchMapping({"/user","/user/self"})
    public ResponseEntity<Void> handleUnsupportedMethods() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).headers(userService.noCacheHeaders()).build();
    }

    @PutMapping("/user/self")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO updateUserRequest) {
        if(!userService.updateUser(updateUserRequest)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/self")
    public ResponseEntity<UserResponse> getUser() {
        UserResponse user =userService.getUser();
        return new ResponseEntity<UserResponse>( user,HttpStatus.OK);
    }
}

