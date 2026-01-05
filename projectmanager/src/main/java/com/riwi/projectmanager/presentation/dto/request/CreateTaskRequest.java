package com.riwi.projectmanager.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "CreateTaskRequest", description = "Datos para crear una tarea")
public class CreateTaskRequest {

    @Schema(example = "Implementar login con JWT")
    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
