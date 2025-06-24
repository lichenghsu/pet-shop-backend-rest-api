package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    @Schema(description = "用戶 ID")
    private Long id;

    @Schema(description = "用戶名稱")
    private String username;

    @Schema(description = "用戶信箱")
    private String email;

    @Schema(description = "用戶角色，例如：USER 或 ADMIN")
    private String role;

    @Schema(description = "註冊時間")
    private LocalDateTime createdAt;

    @Schema(description = "是否啟用帳號")
    private boolean isEnabled;
}
