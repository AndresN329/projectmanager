package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.domain.model.ProjectStatus;
import com.riwi.projectmanager.infrastructure.persistence.entity.ProjectEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-08T20:30:34-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toDomain(ProjectEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        UUID ownerId = null;
        String name = null;
        ProjectStatus status = null;
        boolean deleted = false;

        id = entity.getId();
        ownerId = entity.getOwnerId();
        name = entity.getName();
        status = entity.getStatus();
        deleted = entity.isDeleted();

        Project project = new Project( id, ownerId, name, status, deleted );

        return project;
    }

    @Override
    public ProjectEntity toEntity(Project domain) {
        if ( domain == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setId( domain.getId() );
        projectEntity.setOwnerId( domain.getOwnerId() );
        projectEntity.setName( domain.getName() );
        projectEntity.setStatus( domain.getStatus() );
        projectEntity.setDeleted( domain.isDeleted() );

        return projectEntity;
    }
}
