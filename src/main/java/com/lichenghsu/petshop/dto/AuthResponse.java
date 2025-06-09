package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "JWT 認證回應")
public class AuthResponse {

    @Schema(description = "JWT Token 字串", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String token;
}
