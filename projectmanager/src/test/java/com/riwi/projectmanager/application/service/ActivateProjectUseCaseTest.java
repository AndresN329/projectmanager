package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.InvalidProjectStateException;
import com.riwi.projectmanager.domain.exception.UnauthorizedActionException;
import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.model.ProjectStatus;
import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.domain.ports.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivateProjectUseCaseTest {

    @Mock
    ProjectRepositoryPort projectRepo;
    @Mock
    TaskRepositoryPort taskRepo;
    @Mock
    CurrentUserPort currentUser;
    @Mock
    AuditLogPort audit;
    @Mock
    NotificationPort notification;

    ActivateProjectService service;

    UUID projectId = UUID.randomUUID();
    UUID ownerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        service = new ActivateProjectService(
                projectRepo, taskRepo, currentUser, audit, notification
        );
    }

    @Test
    void ActivateProject_WithTasks_ShouldSucceed() {
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepo.findActiveByProjectId(projectId)).thenReturn(List.of(
                new Task(UUID.randomUUID(), projectId, "task", false, false)
        ));
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        service.activate(projectId);

        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        verify(projectRepo).save(project);
        verify(audit).register("PROJECT_ACTIVATED", projectId);
        verify(notification).notify(anyString());
    }

    @Test
    void ActivateProject_WithoutTasks_ShouldFail() {
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepo.findActiveByProjectId(projectId)).thenReturn(List.of());
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        assertThrows(InvalidProjectStateException.class,
                () -> service.activate(projectId));
    }

    @Test
    void ActivateProject_ByNonOwner_ShouldFail() {
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(UUID.randomUUID());

        assertThrows(UnauthorizedActionException.class,
                () -> service.activate(projectId));
    }
}
