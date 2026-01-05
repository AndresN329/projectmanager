package com.riwi.projectmanager.presentation.dto.response;

import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        String status
) {}
