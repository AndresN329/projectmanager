package com.riwi.projectmanager.infrastructure.persistence.repository;

import com.riwi.projectmanager.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaTaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByProjectIdAndDeletedFalse(UUID projectId);
}
