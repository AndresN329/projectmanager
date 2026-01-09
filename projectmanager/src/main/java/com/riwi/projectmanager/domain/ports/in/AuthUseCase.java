package com.riwi.projectmanager.domain.ports.in;

import java.util.UUID;

public interface AuthUseCase {
    UUID register(String username, String email, String password);
    String login(String email, String password);
}
