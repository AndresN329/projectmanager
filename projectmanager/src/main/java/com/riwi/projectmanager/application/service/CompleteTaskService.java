package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.domain.ports.in.CompleteTaskUseCase;
import com.riwi.projectmanager.domain.ports.out.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompleteTaskService implements CompleteTaskUseCase {

    private final TaskRepositoryPort taskRepo;
    private final ProjectRepositoryPort projectRepo;
    private final CurrentUserPort currentUser;
    private final AuditLogPort audit;
    private final NotificationPort notification;

    public CompleteTaskService(TaskRepositoryPort taskRepo,
                               ProjectRepositoryPort projectRepo,
                               CurrentUserPort currentUser,
                               AuditLogPort audit,
                               NotificationPort notification) {
        this.taskRepo = taskRepo;
        this.projectRepo = projectRepo;
        this.currentUser = currentUser;
        this.audit = audit;
        this.notification = notification;
    }

    @Override
    public void complete(UUID taskId) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new BusinessException("Task not found"));

        Project project = projectRepo.findById(task.getProjectId())
                .orElseThrow(() -> new BusinessException("Project not found"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new UnauthorizedActionException("Only owner can complete tasks");
        }

        if (task.isCompleted()) {
            throw new BusinessException("Task already completed");
        }

        task.complete();
        taskRepo.save(task);

        audit.register("TASK_COMPLETED", taskId);
        notification.notify("Task completed");
    }
}
