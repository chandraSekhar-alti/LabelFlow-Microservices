package com.labelflow.labelchange.auth.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valida email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
