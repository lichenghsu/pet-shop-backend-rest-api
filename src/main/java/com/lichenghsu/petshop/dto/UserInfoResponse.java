package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "使用者資訊回傳格式")
public class UserInfoResponse {

    @Schema(description = "帳號名稱", example = "admin")
    private String username;

    @Schema(description = "Email", example = "admin@example.com")
    private String email;
}
