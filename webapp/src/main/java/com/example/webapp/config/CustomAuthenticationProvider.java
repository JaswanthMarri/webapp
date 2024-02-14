package com.example.webapp.config;

import com.example.webapp.entity.UserAccount;
import com.example.webapp.repo.UserAccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Autowired private UserAccountRepo userRepo;
  @Autowired
  PasswordEncoder passwordEncoder;
  //@Autowired SecurityBeansConfig sc;
  //  public CustomAuthenticationProvider(UserAccountRepo userRepo) {
  //    this.userRepo = userRepo;
  //  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    // Implement your token-based authentication logic here
    //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    log.info("Im am here");
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    log.info(password);
    log.info(passwordEncoder.encode(password));
    UserAccount ua = userRepo.findByUsername(username);
    //log.info(ua.getPassword());
    if (ua.equals(null) || !passwordEncoder.matches( password, ua.getPassword())){ //ua.getPassword() != password) {
      //log.info(ua.getPassword());
      throw new AuthenticationServiceException(username);
    }
    return new UsernamePasswordAuthenticationToken(username, password, null);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
