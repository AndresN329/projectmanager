package com.riwi.projectmanager.domain.ports.out;

import com.riwi.projectmanager.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepositoryPort {
    Optional<Project> findById(UUID id);
    Project save(Project project);
    List<Project> findAllByOwnerId(UUID ownerId);

}
