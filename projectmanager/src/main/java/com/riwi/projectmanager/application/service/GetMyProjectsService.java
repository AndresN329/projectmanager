package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.ports.in.GetMyProjectsUseCase;
import com.riwi.projectmanager.domain.ports.out.CurrentUserPort;
import com.riwi.projectmanager.domain.ports.out.ProjectRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMyProjectsService implements GetMyProjectsUseCase {

    private final ProjectRepositoryPort projectRepo;
    private final CurrentUserPort currentUser;

    public GetMyProjectsService(ProjectRepositoryPort projectRepo,
                                CurrentUserPort currentUser) {
        this.projectRepo = projectRepo;
        this.currentUser = currentUser;
    }

    @Override
    public List<Project> getMyProjects() {
        return projectRepo.findAllByOwnerId(currentUser.getCurrentUserId());
    }
}
