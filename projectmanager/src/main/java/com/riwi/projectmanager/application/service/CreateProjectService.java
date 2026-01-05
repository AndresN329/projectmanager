package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.model.ProjectStatus;
import com.riwi.projectmanager.domain.ports.in.CreateProjectUseCase;
import com.riwi.projectmanager.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanager.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateProjectService implements CreateProjectUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final CurrentUserPort currentUser;

    public CreateProjectService(ProjectRepositoryPort projectRepo,
                                CurrentUserPort currentUser) {
        this.projectRepo = projectRepo;
        this.currentUser = currentUser;
    }

    @Override
    public UUID create(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException("Project name is required");
        }

        UUID userId = currentUser.getCurrentUserId(); // si no hay auth, aquí lanza excepción

        Project project = new Project(
                UUID.randomUUID(),
                userId,
                name,
                ProjectStatus.DRAFT,
                false
        );

        projectRepo.save(project);
        return project.getId();
    }
}
