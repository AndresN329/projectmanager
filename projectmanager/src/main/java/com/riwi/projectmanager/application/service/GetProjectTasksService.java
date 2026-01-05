package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.domain.ports.in.GetProjectTasksUseCase;
import com.riwi.projectmanager.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetProjectTasksService implements GetProjectTasksUseCase {

    private final TaskRepositoryPort taskRepo;

    public GetProjectTasksService(TaskRepositoryPort taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public List<Task> getTasks(UUID projectId) {
        // usa el que prefieras:
        return taskRepo.findActiveByProjectId(projectId);
        // o: return taskRepo.findAllByProjectId(projectId);
    }
}
