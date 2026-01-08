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

    // Mocks for all external dependencies of the use case
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

    // System under test
    ActivateProjectService service;

    UUID projectId = UUID.randomUUID();
    UUID ownerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // Create the use case with mocked dependencies
        service = new ActivateProjectService(
                projectRepo, taskRepo, currentUser, audit, notification
        );
    }

    @Test
    void ActivateProject_WithTasks_ShouldSucceed() {
        // GIVEN a draft project owned by the current user and at least one active task
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepo.findActiveByProjectId(projectId)).thenReturn(List.of(
                new Task(UUID.randomUUID(), projectId, "task", false, false)
        ));
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        // WHEN the project is activated
        service.activate(projectId);

        // THEN the project status changes to ACTIVE and side effects are triggered
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        verify(projectRepo).save(project);
        verify(audit).register("PROJECT_ACTIVATED", projectId);
        verify(notification).notify(anyString());
    }

    @Test
    void ActivateProject_WithoutTasks_ShouldFail() {
        // GIVEN a draft project without any tasks
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(taskRepo.findActiveByProjectId(projectId)).thenReturn(List.of());
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        // THEN activating the project is not allowed
        assertThrows(InvalidProjectStateException.class,
                () -> service.activate(projectId));
    }

    @Test
    void ActivateProject_ByNonOwner_ShouldFail() {
        // GIVEN a project owned by another user
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.DRAFT, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(UUID.randomUUID());

        // THEN a non-owner cannot activate the project
        assertThrows(UnauthorizedActionException.class,
                () -> service.activate(projectId));
    }

    @Test
    void ActivateProject_AlreadyActive_ShouldFail() {
        // GIVEN a project that is already ACTIVE
        Project project = new Project(projectId, ownerId, "Test", ProjectStatus.ACTIVE, false);

        when(projectRepo.findById(projectId)).thenReturn(Optional.of(project));
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        // THEN activating an already active project is not allowed
        assertThrows(InvalidProjectStateException.class,
                () -> service.activate(projectId));
    }
}
