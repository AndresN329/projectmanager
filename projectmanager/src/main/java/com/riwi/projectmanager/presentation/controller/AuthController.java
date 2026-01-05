package com.riwi.projectmanager.presentation.controller;

import com.riwi.projectmanager.infrastructure.security.entity.UserEntity;
import com.riwi.projectmanager.infrastructure.security.jwt.JwtService;
import com.riwi.projectmanager.infrastructure.security.repository.JpaUserRepository;
import com.riwi.projectmanager.presentation.dto.request.LoginRequest;
import com.riwi.projectmanager.presentation.dto.request.RegisterRequest;
import com.riwi.projectmanager.presentation.dto.response.RegisterResponse;
import com.riwi.projectmanager.presentation.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Registro e inicio de sesión")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JpaUserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(JpaUserRepository userRepo, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Register", description = "Crea un usuario con username, email y password.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest body) {
        String username = body.getUsername();
        String email = body.getEmail();
        String password = body.getPassword();

        if (userRepo.existsByEmail(email)) return ResponseEntity.badRequest().body("Email already exists");
        if (userRepo.existsByUsername(username)) return ResponseEntity.badRequest().body("Username already exists");

        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));

        userRepo.save(u);
        return ResponseEntity.ok(new RegisterResponse(u.getId().toString()));
    }

    @Operation(summary = "Login", description = "Valida credenciales y retorna un JWT.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest body) {
        String email = body.getEmail();
        String password = body.getPassword();

        UserEntity u = userRepo.findByEmail(email).orElse(null);
        if (u == null || !encoder.matches(password, u.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtService.generate(u.getId());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
