package com.riwi.projectmanager.presentation.controller;

import com.riwi.projectmanager.domain.ports.in.AuthUseCase;
import com.riwi.projectmanager.presentation.dto.request.LoginRequest;
import com.riwi.projectmanager.presentation.dto.request.RegisterRequest;
import com.riwi.projectmanager.presentation.dto.response.RegisterResponse;
import com.riwi.projectmanager.presentation.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Registro e inicio de sesión")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase auth;

    public AuthController(AuthUseCase auth) {
        this.auth = auth;
    }

    @Operation(summary = "Register", description = "Crea un usuario con username, email y password.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest body) {
        var id = auth.register(body.getUsername(), body.getEmail(), body.getPassword());
        return ResponseEntity
                .status(201)
                .body(new RegisterResponse(id.toString()));
    }

    @Operation(summary = "Login", description = "Valida credenciales y retorna un JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest body) {
        String token = auth.login(body.getEmail(), body.getPassword());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
