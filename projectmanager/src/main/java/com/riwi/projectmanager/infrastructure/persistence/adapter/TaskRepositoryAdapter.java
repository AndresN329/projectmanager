package com.riwi.projectmanager.infrastructure.persistence.adapter;

import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.domain.ports.out.TaskRepositoryPort;
import com.riwi.projectmanager.infrastructure.persistence.mapper.TaskMapper;
import com.riwi.projectmanager.infrastructure.persistence.repository.JpaTaskRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class TaskRepositoryAdapter implements TaskRepositoryPort {

    private final JpaTaskRepository repo;
    private final TaskMapper mapper;

    public TaskRepositoryAdapter(JpaTaskRepository repo,
                                 TaskMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Task> findActiveByProjectId(UUID projectId) {
        return List.of();
    }

    @Override
    public List<Task> findAllByProjectId(UUID projectId) {
        return repo.findByProjectIdAndDeletedFalse(projectId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Task save(Task task) {
        repo.save(mapper.toEntity(task));
        return task;
    }
}
