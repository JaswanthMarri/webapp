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

       // return new BCryptPasswordEncoder(10, new CustomSalt());
    }
//    private static class CustomSalt implements BCryptPasswordEncoder.SaltGenerator {
//        @Override
//        public byte[] generateSalt(int length) {
//            return CUSTOM_SALT.getBytes();
//        }
//    }
}
