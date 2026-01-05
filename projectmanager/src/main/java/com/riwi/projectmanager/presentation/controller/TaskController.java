package com.riwi.projectmanager.presentation.controller;

import com.riwi.projectmanager.domain.ports.in.CompleteTaskUseCase;
import com.riwi.projectmanager.domain.ports.in.CreateTaskUseCase;
import com.riwi.projectmanager.domain.ports.in.GetProjectTasksUseCase;
import com.riwi.projectmanager.presentation.dto.request.CreateTaskRequest;
import com.riwi.projectmanager.presentation.dto.response.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Tasks", description = "Gestión de tareas de proyectos")
@RestController
@RequestMapping("/api")
public class TaskController {

    private final CreateTaskUseCase createTask;
    private final CompleteTaskUseCase completeTask;
    private final GetProjectTasksUseCase getProjectTasks;

    public TaskController(CreateTaskUseCase createTask,
                          CompleteTaskUseCase completeTask,
                          GetProjectTasksUseCase getProjectTasks) {
        this.createTask = createTask;
        this.completeTask = completeTask;
        this.getProjectTasks = getProjectTasks;
    }

    @Operation(
            summary = "Crear tarea",
            description = "Crea una tarea asociada a un proyecto del usuario autenticado."
    )
    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<UUID> create(
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateTaskRequest body) {

        UUID id = createTask.create(projectId, body.getTitle());
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Listar tareas del proyecto",
            description = "Lista las tareas de un proyecto del usuario autenticado."
    )
    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> listByProject(@PathVariable UUID projectId) {
        var tasks = getProjectTasks.getTasks(projectId)
                .stream()
                .map(t -> new TaskResponse(
                        t.getId(),
                        t.getProjectId(),
                        t.getTitle(),
                        t.isCompleted()
                ))
                .toList();

        return ResponseEntity.ok(tasks);
    }

    @Operation(
            summary = "Completar tarea",
            description = "Marca una tarea como completada."
    )
    @PatchMapping("/tasks/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable UUID id) {
        completeTask.complete(id);
        return ResponseEntity.noContent().build();
    }
}
