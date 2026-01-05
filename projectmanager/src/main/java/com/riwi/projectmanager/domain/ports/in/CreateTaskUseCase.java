package com.riwi.projectmanager.domain.ports.in;

import java.util.UUID;

public interface CreateTaskUseCase {

    UUID create(UUID projectId, String title);
}
