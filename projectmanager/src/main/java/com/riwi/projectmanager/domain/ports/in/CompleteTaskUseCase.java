package com.riwi.projectmanager.domain.ports.in;

import java.util.UUID;

public interface CompleteTaskUseCase {
    void complete(UUID taskId);
}
