package com.riwi.projectmanager.infrastructure.security.adapter;

import com.riwi.projectmanager.domain.ports.out.TokenGeneratorPort;
import com.riwi.projectmanager.infrastructure.security.jwt.JwtService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtTokenGeneratorAdapter implements TokenGeneratorPort {

    private final JwtService jwtService;

    public JwtTokenGeneratorAdapter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generate(UUID userId) {
        return jwtService.generate(userId);
    }
}
