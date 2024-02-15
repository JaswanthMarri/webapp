package com.example.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityBeansConfig {

  // private static final String CUSTOM_SALT = "hash";

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder(passwordEncoder());
    return new CustomSaltBCryptPasswordEncoder();
  }
}
