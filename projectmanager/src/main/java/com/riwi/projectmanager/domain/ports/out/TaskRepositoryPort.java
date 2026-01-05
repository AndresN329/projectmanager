package com.riwi.projectmanager.domain.ports.out;

import com.riwi.projectmanager.domain.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepositoryPort {
    Optional<Task> findById(UUID id);
    List<Task> findActiveByProjectId(UUID projectId); // deleted=false
    Task save(Task task);
    List<Task> findAllByProjectId(UUID projectId);

}
