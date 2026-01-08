package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Project;
import com.riwi.projectmanager.infrastructure.persistence.entity.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    Project toDomain(ProjectEntity entity);

    ProjectEntity toEntity(Project domain);
}
