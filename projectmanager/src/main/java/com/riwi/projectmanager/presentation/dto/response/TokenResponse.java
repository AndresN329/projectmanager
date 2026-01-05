package com.riwi.projectmanager.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TokenResponse")
public class TokenResponse {

    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    public TokenResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
