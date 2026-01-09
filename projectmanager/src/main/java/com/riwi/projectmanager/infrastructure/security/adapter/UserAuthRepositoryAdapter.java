package com.riwi.projectmanager.infrastructure.security.adapter;

import com.riwi.projectmanager.domain.ports.out.UserAuthRepositoryPort;
import com.riwi.projectmanager.infrastructure.security.entity.UserEntity;
import com.riwi.projectmanager.infrastructure.security.repository.JpaUserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserAuthRepositoryAdapter implements UserAuthRepositoryPort {

    private final JpaUserRepository repo;

    public UserAuthRepositoryAdapter(JpaUserRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<UserAuthView> findByEmail(String email) {
        return repo.findByEmail(email)
                .map(u -> new UserAuthView(u.getId(), u.getPassword()));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public UUID create(String username, String email, String passwordHash) {
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(passwordHash);
        return repo.save(u).getId();
    }
}
