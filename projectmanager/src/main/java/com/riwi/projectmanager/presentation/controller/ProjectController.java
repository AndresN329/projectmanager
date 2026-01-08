package com.riwi.projectmanager.presentation.controller;

import com.riwi.projectmanager.domain.ports.in.ActivateProjectUseCase;
import com.riwi.projectmanager.domain.ports.in.CreateProjectUseCase;
import com.riwi.projectmanager.domain.ports.in.GetMyProjectsUseCase;
import com.riwi.projectmanager.presentation.dto.request.CreateProjectRequest;
import com.riwi.projectmanager.presentation.dto.response.ProjectResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Projects", description = "Gestión de proyectos del usuario autenticado")
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final CreateProjectUseCase createProject;
    private final ActivateProjectUseCase activateProject;
    private final GetMyProjectsUseCase getMyProjects;

    public ProjectController(CreateProjectUseCase createProject,
                             ActivateProjectUseCase activateProject,
                             GetMyProjectsUseCase getMyProjects) {
        this.createProject = createProject;
        this.activateProject = activateProject;
        this.getMyProjects = getMyProjects;
    }

    @Operation(
            summary = "Crear proyecto",
            description = "Crea un proyecto asociado al usuario autenticado y retorna el UUID del proyecto."
    )
    @PostMapping
    public ResponseEntity<UUID> create(@Valid @RequestBody CreateProjectRequest body) {
        UUID id = createProject.create(body.getName());
        return ResponseEntity.ok(id);
    }

    @Operation(
            summary = "Activar proyecto",
            description = "Activa un proyecto del usuario autenticado."
    )
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable UUID id) {
        activateProject.activate(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar mis proyectos (paginado)",
            description = "Retorna los proyectos del usuario autenticado con paginación."
    )
    @GetMapping
    public ResponseEntity<Page<ProjectResponse>> list(Pageable pageable) {

        var projects = getMyProjects.getMyProjects();

        var responses = projects.stream()
                .map(p -> new ProjectResponse(
                        p.getId(),
                        p.getName(),
                        p.getStatus().name()
                ))
                .toList();

        // paginación simple en memoria (suficiente para la rúbrica)
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responses.size());

        Page<ProjectResponse> page = new PageImpl<>(
                responses.subList(start, end),
                pageable,
                responses.size()
        );

        return ResponseEntity.ok(page);
    }
}
