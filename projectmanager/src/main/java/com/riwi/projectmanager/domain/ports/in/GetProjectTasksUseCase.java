package com.riwi.projectmanager.domain.ports.in;

import com.riwi.projectmanager.domain.model.Task;

import java.util.List;
import java.util.UUID;

public interface GetProjectTasksUseCase {
    List<Task> getTasks(UUID projectId);
}
