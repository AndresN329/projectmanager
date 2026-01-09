package com.riwi.projectmanager.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthRepositoryPort {

    record UserAuthView(UUID id, String passwordHash) {}

    Optional<UserAuthView> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    UUID create(String username, String email, String passwordHash);
}
