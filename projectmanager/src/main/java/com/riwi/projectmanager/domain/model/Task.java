package com.riwi.projectmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "id")
public class Task {

    private final UUID id;
    private final UUID projectId;
    private final String title;

    private boolean completed;
    private boolean deleted;

    public void complete() {
        if (deleted) {
            throw new IllegalStateException("Task is deleted");
        }
        if (completed) {
            throw new IllegalStateException("Task already completed");
        }
        this.completed = true;
    }
}
