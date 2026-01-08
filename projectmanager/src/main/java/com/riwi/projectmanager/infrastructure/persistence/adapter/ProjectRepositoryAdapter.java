package com.riwi.projectmanager.infrastructure.persistence.adapter;

import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.ports.out.ProjectRepositoryPort;
import com.riwi.projectmanager.infrastructure.persistence.mapper.ProjectMapper;
import com.riwi.projectmanager.infrastructure.persistence.repository.JpaProjectRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProjectRepositoryAdapter implements ProjectRepositoryPort {

    private final JpaProjectRepository repo;
    private final ProjectMapper mapper;

    public ProjectRepositoryAdapter(JpaProjectRepository repo,
                                    ProjectMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return repo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Project save(Project project) {
        repo.save(mapper.toEntity(project));
        return project;
    }

    @Override
    public List<Project> findAllByOwnerId(UUID ownerId) {
        return repo.findByOwnerIdAndDeletedFalse(ownerId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
