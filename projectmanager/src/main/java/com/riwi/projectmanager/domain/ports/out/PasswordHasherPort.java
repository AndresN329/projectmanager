package com.riwi.projectmanager.domain.ports.out;

public interface PasswordHasherPort {
    String encode(String raw);
    boolean matches(String raw, String hash);
}
