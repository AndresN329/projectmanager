package com.riwi.projectmanager.infrastructure.persistence.repository;

import com.riwi.projectmanager.infrastructure.persistence.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProjectRepository extends JpaRepository<ProjectEntity, UUID> {

    Page<ProjectEntity> findByOwnerIdAndDeletedFalse(UUID ownerId, Pageable pageable);
}
