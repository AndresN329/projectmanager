package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.exception.InvalidProjectStateException;
import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.ports.in.ActivateProjectUseCase;
import com.riwi.projectmanager.domain.ports.out.AuditLogPort;
import com.riwi.projectmanager.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanager.domain.ports.out.NotificationPort;
import com.riwi.projectmanager.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanager.domain.ports.out.TaskRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ActivateProjectService implements ActivateProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final TaskRepositoryPort taskRepo;
    private final CurrentUserPort currentUser;
    private final AuditLogPort audit;
    private final NotificationPort notification;

    public ActivateProjectService(ProjectRepositoryPort projectRepo,
                                  TaskRepositoryPort taskRepo,
                                  CurrentUserPort currentUser,
                                  AuditLogPort audit,
                                  NotificationPort notification) {
        this.projectRepo = projectRepo;
        this.taskRepo = taskRepo;
        this.currentUser = currentUser;
        this.audit = audit;
        this.notification = notification;
    }

    @Override
    public void activate(UUID projectId) {

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new BusinessException("Project not found"));

        if (!project.getOwnerId().equals(currentUser.getCurrentUserId())) {
            throw new UnauthorizedActionException("Only owner can activate project");
        }

        if (taskRepo.findActiveByProjectId(projectId).isEmpty()) {
            throw new InvalidProjectStateException("Project needs at least one task");
        }

        project.activate();
        projectRepo.save(project);

        audit.register("PROJECT_ACTIVATED", projectId);
        notification.notify("Project activated");
    }
}