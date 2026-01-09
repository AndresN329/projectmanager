package com.riwi.projectmanager.domain.ports.out;

import java.util.UUID;

public interface TokenGeneratorPort {
    String generate(UUID userId);
}
