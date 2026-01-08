package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.infrastructure.persistence.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toDomain(TaskEntity entity);

    TaskEntity toEntity(Task domain);
}

