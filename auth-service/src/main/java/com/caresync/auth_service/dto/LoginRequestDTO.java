package com.caresync.auth_service.dto;

import com.caresync.auth_service.validation.AuthValidationMessages;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    
    @NotBlank(message = AuthValidationMessages.loginEmailRequired)
    @Email(message = AuthValidationMessages.loginEmailInvalid)
    private String email;

    @NotBlank(message = AuthValidationMessages.loginPasswordRequired)
    @Size(min = 8, message = AuthValidationMessages.loginPasswordInvalidMin)
    @Size(max = 32, message = AuthValidationMessages.loginPasswordInvalidMax)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
