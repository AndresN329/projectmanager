package com.riwi.projectmanager.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterResponse")
public class RegisterResponse {
    @Schema(example = "b3f3f2f2-2f2f-4f4f-8a8a-aaaaaaaaaaaa")
    private String id;

    public RegisterResponse(String id) { this.id = id; }
    public String getId() { return id; }
}
