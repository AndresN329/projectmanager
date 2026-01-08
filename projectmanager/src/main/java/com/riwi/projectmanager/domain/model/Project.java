package com.riwi.projectmanager.domain.model;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.exception.InvalidProjectStateException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
public class Project {

    private final UUID id;
    private final UUID ownerId;
    private final String name;
    private ProjectStatus status;
    private boolean deleted;

    public Project(UUID id,
                   UUID ownerId,
                   String name,
                   ProjectStatus status,
                   boolean deleted) {

        if (id == null) throw new IllegalArgumentException("id is required");
        if (ownerId == null) throw new IllegalArgumentException("ownerId is required");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name is required");

        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.status = status;
        this.deleted = deleted;
    }

    public void activate() {
        if (deleted) {
            throw new BusinessException("Project is deleted");
        }
        if (status == ProjectStatus.ACTIVE) {
            throw new InvalidProjectStateException("Project already active");
        }
        this.status = ProjectStatus.ACTIVE;
    }

    public boolean isActive() {
        return status == ProjectStatus.ACTIVE;
    }
}
