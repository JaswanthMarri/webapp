package com.example.webapp.config;

import com.example.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable() // Disable CSRF protection for simplicity
            .authorizeRequests()
            .antMatchers("/v1/user/self")
            .authenticated() // Secure endpoints
            //.anyRequest()
            //.permitAll() // Allow all other requests without authentication
            .and()
            .httpBasic(); // Enable basic authentication

  }
//@Override
//protected void configure(HttpSecurity http) throws Exception {
//  http.csrf().disable() // Disable CSRF protection for simplicity
//          .authorizeRequests()
//          .antMatchers("/v1/user","healthz").permitAll() // Allow login endpoint without authentication
//          .anyRequest().authenticated() // Secure all other endpoints
//          .and()
//          .addFilterBefore(new TokenAuthenticationFilter(userService), UsernamePasswordAuthenticationFilter.class); // Add token authentication filter
//}
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).userDetailsPasswordManager(userService).passwordEncoder(passwordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
