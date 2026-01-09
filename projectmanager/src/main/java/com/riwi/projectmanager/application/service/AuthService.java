package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.ports.in.AuthUseCase;
import com.riwi.projectmanager.domain.ports.out.PasswordHasherPort;
import com.riwi.projectmanager.domain.ports.out.TokenGeneratorPort;
import com.riwi.projectmanager.domain.ports.out.UserAuthRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService implements AuthUseCase {

    private final UserAuthRepositoryPort users;
    private final PasswordHasherPort hasher;
    private final TokenGeneratorPort tokens;

    public AuthService(UserAuthRepositoryPort users,
                       PasswordHasherPort hasher,
                       TokenGeneratorPort tokens) {
        this.users = users;
        this.hasher = hasher;
        this.tokens = tokens;
    }

    @Override
    public UUID register(String username, String email, String password) {
        if (users.existsByEmail(email)) throw new IllegalArgumentException("Email already exists");
        if (users.existsByUsername(username)) throw new IllegalArgumentException("Username already exists");

        UUID id = users.create(username, email, hasher.encode(password));
        return id;
    }

    @Override
    public String login(String email, String password) {
        var user = users.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedActionException("Invalid credentials"));

        if (!hasher.matches(password, user.passwordHash())) {
            throw new UnauthorizedActionException("Invalid credentials");
        }

        return tokens.generate(user.id());
    }
}
