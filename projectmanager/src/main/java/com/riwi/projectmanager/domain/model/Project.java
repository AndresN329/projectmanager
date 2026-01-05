package com.riwi.projectmanager.domain.model;

import com.riwi.projectmanager.domain.exception.BusinessException;
import com.riwi.projectmanager.domain.exception.InvalidProjectStateException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(force = true)
@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Project {

    private final UUID id;
    private final UUID ownerId;
    private final String name;
    private ProjectStatus status;
    private boolean deleted;

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
