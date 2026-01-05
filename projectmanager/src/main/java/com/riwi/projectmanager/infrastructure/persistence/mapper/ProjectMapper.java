package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.infrastructure.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = java.util.UUID.class)
public interface ProjectMapper {

    Project toDomain(ProjectEntity entity);

    @Mapping(target = "id", expression = "java(domain.getId() != null ? domain.getId() : UUID.randomUUID())")
    ProjectEntity toEntity(Project domain);
}
