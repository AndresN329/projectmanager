package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.domain.ports.in.CreateTaskUseCase;
import com.riwi.projectmanager.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanager.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanager.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateTaskService implements CreateTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final CurrentUserPort currentUser;

    public CreateTaskService(TaskRepositoryPort taskRepo,
                             ProjectRepositoryPort projectRepo,
                             CurrentUserPort currentUser) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.currentUser = currentUser;
    }

    @Override
    public UUID create(UUID projectId, String title) {

        if (title == null || title.isBlank()) {
            throw new BusinessException("Task title is required");
        }

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new BusinessException("Project not found"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new UnauthorizedActionException("Only owner can add tasks");
        }

        Task task = new Task(
                UUID.randomUUID(),
                projectId,
                title,
                false,
                false
        );

        taskRepo.save(task);
        return task.getId();
    }
}
