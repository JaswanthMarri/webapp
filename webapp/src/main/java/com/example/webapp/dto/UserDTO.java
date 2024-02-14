package com.example.webapp.dto;

import com.example.webapp.constants.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank(message = "username is required")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @Pattern(
            regexp = Constants.EMAIL_REGEX,
            flags = Pattern.Flag.UNICODE_CASE,
            message = "username should have email pattern")
    private String username;

    @NotBlank(message = "password is required")
    @Size(min = 6, max =20,  message = "Name must be between 1 and 50 characters")
    private String password;

    @JsonProperty("first_name")
    @NotBlank(message = "firstname is required")
    @Size(min = 1, max =20,  message = "Name must be between 1 and 50 characters")
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "lastname is required")
    @Size(min = 1, max =20,  message = "Name must be between 1 and 50 characters")
    private String lastName;
}
