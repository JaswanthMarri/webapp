package com.example.webapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class UserResponse {

    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("account_created")
    private LocalDateTime accountCreated;

    @JsonProperty("account_updated")
    private LocalDateTime accountUpdated;
}
