package com.riwi.projectmanager.presentation.dto.response;

import java.util.UUID;

public record TaskResponse(
        UUID id,
        UUID projectId,
        String title,
        boolean completed
) {}
