package com.riwi.projectmanager.domain.ports.in;

import java.util.UUID;

public interface CreateProjectUseCase {

    UUID create(String name);
}
