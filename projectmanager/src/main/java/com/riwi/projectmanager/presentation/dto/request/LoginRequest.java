package com.riwi.projectmanager.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "LoginRequest", description = "Datos para iniciar sesión")
public class LoginRequest {

    @Schema(example = "coder@riwi.com")
    @Email @NotBlank
    private String email;

    @Schema(example = "123456")
    @NotBlank
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
