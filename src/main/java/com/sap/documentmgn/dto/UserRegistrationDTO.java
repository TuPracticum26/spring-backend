package com.sap.documentmgn.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/])[A-Za-z\\d!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/]+$",
            message = "Password must contain at least one letter, one number and one special character."
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
