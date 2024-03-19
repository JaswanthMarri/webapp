package com.example.webapp.dto;

import com.example.webapp.constants.Constants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraintvalidation.SupportedValidationTarget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserUpdateDTO {

  private String password;

  @JsonProperty("first_name")
  @NotBlank(message = "firstname is required")
  @Size(min = 1, max = 20, message = "Name must be between 1 and 50 characters")
  private String firstName;

  @JsonProperty("last_name")
  @NotBlank(message = "lastname is required")
  @Size(min = 1, max = 20, message = "Name must be between 1 and 50 characters")
  private String lastName;
}
