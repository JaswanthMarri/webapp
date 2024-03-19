package com.example.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.webapp.repo")
@EntityScan("com.example.webapp.entity")
public class WebappApplication {

  public static void main(String[] args) {

    SpringApplication.run(WebappApplication.class, args);
  }
}
