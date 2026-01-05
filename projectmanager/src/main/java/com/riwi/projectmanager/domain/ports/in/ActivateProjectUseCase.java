package com.riwi.projectmanager.domain.ports.in;

import java.util.UUID;

public interface ActivateProjectUseCase {
    void activate(UUID projectId);
}
