package com.riwi.projectmanager.application.service;

import com.riwi.projectmanager.domain.exception.BusinessException;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompleteTaskUseCaseTest {

    // Mocks for external dependencies
    @Mock
    TaskRepositoryPort taskRepo;
    @Mock
    ProjectRepositoryPort projectRepo;
    @Mock
    CurrentUserPort currentUser;
    @Mock
    AuditLogPort audit;
    @Mock
    NotificationPort notification;

    // System under test
    CompleteTaskService service;

    UUID taskId = UUID.randomUUID();
    UUID projectId = UUID.randomUUID();
    UUID ownerId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        // Create the use case with mocked dependencies
        service = new CompleteTaskService(
                taskRepo, projectRepo, currentUser, audit, notification
        );
    }

    @Test
    void CompleteTask_AlreadyCompleted_ShouldFail() {
        // GIVEN a task that is already completed and owned by the current user
        Task task = new Task(taskId, projectId, "task", true, false);

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepo.findById(projectId))
                .thenReturn(Optional.of(new Project(projectId, ownerId, "P", ProjectStatus.ACTIVE, false)));
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        // THEN completing an already completed task is not allowed
        assertThrows(BusinessException.class,
                () -> service.complete(taskId));
    }

    @Test
    void CompleteTask_ShouldGenerateAuditAndNotification() {
        // GIVEN a pending task owned by the current user
        Task task = new Task(taskId, projectId, "task", false, false);

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepo.findById(projectId))
                .thenReturn(Optional.of(new Project(projectId, ownerId, "P", ProjectStatus.ACTIVE, false)));
        when(currentUser.getCurrentUserId()).thenReturn(ownerId);

        // WHEN the task is completed
        service.complete(taskId);

        // THEN the task is marked as completed and side effects are triggered
        assertTrue(task.isCompleted());
        verify(taskRepo).save(task);
        verify(audit).register("TASK_COMPLETED", taskId);
        verify(notification).notify(anyString());
    }

    @Test
    void CompleteTask_ByNonOwner_ShouldFail() {
        // GIVEN a task owned by another user
        Task task = new Task(taskId, projectId, "task", false, false);

        when(taskRepo.findById(taskId)).thenReturn(Optional.of(task));
        when(projectRepo.findById(projectId))
                .thenReturn(Optional.of(new Project(projectId, ownerId, "P", ProjectStatus.ACTIVE, false)));
        when(currentUser.getCurrentUserId()).thenReturn(UUID.randomUUID());

        // THEN a non-owner cannot complete the task
        assertThrows(UnauthorizedActionException.class,
                () -> service.complete(taskId));
    }
}
