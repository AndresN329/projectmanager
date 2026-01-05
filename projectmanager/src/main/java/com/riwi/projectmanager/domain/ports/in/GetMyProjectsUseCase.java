package com.riwi.projectmanager.domain.ports.in;

import com.riwi.projectmanager.domain.model.Project;
import java.util.List;

public interface GetMyProjectsUseCase {
    List<Project> getMyProjects();
}
