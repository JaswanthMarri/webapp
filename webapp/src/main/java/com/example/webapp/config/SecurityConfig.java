package com.example.webapp.config;

import com.example.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//
// import static org.springframework.security.config.Customizer.withDefaults;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import
// org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.DefaultSecurityFilterChain;
//
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
//
//  @Bean
//  public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.authorizeRequests(
//            authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/v1/user")
//                    .authenticated()
//                    .anyRequest()
//                    .permitAll())
//        .httpBasic(withDefaults());
//
//    return http.build();
//  }
//
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//        .withUser("user")
//        .password(passwordEncoder().encode("password"));
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
// }
// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {//} extends WebSecurityConfigurerAdapter {
//
//  private static final String AUTHENTICATED_ENDPOINT = "/v1/user";
//
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            .authorizeRequests()
//            .antMatchers(AUTHENTICATED_ENDPOINT).authenticated() // Secure the desired endpoint
//            .anyRequest().permitAll() // Allow access to other endpoints
//            .and()
//            .httpBasic(); // Enable basic authentication
//
//    return http.build();
//  }
//
//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder(); // Consider stronger options in production
//  }
//
//  @Autowired
//  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//    auth.inMemoryAuthentication()
//            .withUser("user1") // Replace with your actual user data
//            .password(passwordEncoder().encode("user1Pass")) // Replace with actual password
//            ;
//  }
// }

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
            .authenticated() // Secure endpoints under /api/authenticated
            .anyRequest()
            .permitAll() // Allow all other requests without authentication
            .and()
            .httpBasic(); // Enable basic authentication

  }
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

//  @Bean
//  public AuthenticationProvider daoAuthenticationProvider() {
//    DaoAuthenticationProvider provider =
//            new DaoAuthenticationProvider();
//    provider.setPasswordEncoder(passwordEncoder);
//    provider.setUserDetailsService(this.userService);
//    return provider;
//  }

}
