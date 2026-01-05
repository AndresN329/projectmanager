package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.infrastructure.persistence.entity.ProjectEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-05T13:28:19-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ProjectMapperImpl implements ProjectMapper {

    @Override
    public Project toDomain(ProjectEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Project project = new Project();

        return project;
    }

    @Override
    public ProjectEntity toEntity(Project domain) {
        if ( domain == null ) {
            return null;
        }

        ProjectEntity projectEntity = new ProjectEntity();

        projectEntity.setOwnerId( domain.getOwnerId() );
        projectEntity.setName( domain.getName() );
        projectEntity.setStatus( domain.getStatus() );
        projectEntity.setDeleted( domain.isDeleted() );

        projectEntity.setId( domain.getId() != null ? domain.getId() : UUID.randomUUID() );

        return projectEntity;
    }
}
