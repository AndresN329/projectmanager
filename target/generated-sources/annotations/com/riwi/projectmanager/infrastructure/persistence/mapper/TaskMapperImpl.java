package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.infrastructure.persistence.entity.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-05T13:16:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toDomain(TaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Task task = new Task();

        return task;
    }

    @Override
    public TaskEntity toEntity(Task domain) {
        if ( domain == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        return taskEntity;
    }
}
