package com.riwi.projectmanager.infrastructure.persistence.mapper;

import com.riwi.projectmanager.domain.model.Task;
import com.riwi.projectmanager.infrastructure.persistence.entity.TaskEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-09T10:32:20-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toDomain(TaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        UUID projectId = null;
        String title = null;
        boolean completed = false;
        boolean deleted = false;

        id = entity.getId();
        projectId = entity.getProjectId();
        title = entity.getTitle();
        completed = entity.isCompleted();
        deleted = entity.isDeleted();

        Task task = new Task( id, projectId, title, completed, deleted );

        return task;
    }

    @Override
    public TaskEntity toEntity(Task domain) {
        if ( domain == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setId( domain.getId() );
        taskEntity.setProjectId( domain.getProjectId() );
        taskEntity.setTitle( domain.getTitle() );
        taskEntity.setCompleted( domain.isCompleted() );
        taskEntity.setDeleted( domain.isDeleted() );

        return taskEntity;
    }
}
