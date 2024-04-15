package com.example.webapp.config;

// import com.example.webapp.filter.TokenAuthenticationFilter;
import com.example.webapp.filter.TokenAuthenticationFilter;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;


  @Bean
  public CustomAuthenticationProvider authenticationProvider() {
    return new CustomAuthenticationProvider();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }


//  @Bean
//  public AuthenticationFailureHandler customAuthenticationFailureHandler() {
//    return new AuthFailureHandler();
//  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable() // Disable CSRF protection for simplicity
        .authorizeRequests()
        .antMatchers("/v5/user/self")
        .authenticated() // Secure endpoints
        // .anyRequest()
        // .permitAll() // Allow all other requests without authentication
        .and()
        //.formLogin()
        //.failureHandler(customAuthenticationFailureHandler())
        //.and()
        //.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .httpBasic(); // Enable basic authentication
  }


}
