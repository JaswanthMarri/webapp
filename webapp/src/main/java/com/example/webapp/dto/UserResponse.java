package com.example.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserResponse {

  private String username;
  private String id;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  @JsonProperty("account_created")
  private LocalDateTime accountCreated;

  @JsonProperty("account_updated")
  private LocalDateTime accountUpdated;
}
