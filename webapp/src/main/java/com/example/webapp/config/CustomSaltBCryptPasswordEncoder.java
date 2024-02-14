package com.example.webapp.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomSaltBCryptPasswordEncoder extends BCryptPasswordEncoder {

    // Define your custom salt
    private static final String CUSTOM_SALT = "your_custom_salt_here";

    public CustomSaltBCryptPasswordEncoder() {
        super(10); // Set the strength factor, e.g., 10
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String saltedPassword = CUSTOM_SALT + rawPassword; // Prepend custom salt
        return super.encode(saltedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String saltedPassword = CUSTOM_SALT + rawPassword; // Prepend custom salt
        return super.matches(saltedPassword, encodedPassword);
    }
}
