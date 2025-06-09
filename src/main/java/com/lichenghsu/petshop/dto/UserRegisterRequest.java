package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "用戶註冊請求資料")
public class UserRegisterRequest {

    @NotBlank
    @Schema(description = "使用者帳號", example = "user123", required = true)
    private String username;

    @NotBlank
    @Schema(description = "使用者密碼", example = "password", required = true)
    private String password;

    @Email
    @Schema(description = "Email 地址", example = "user@example.com", required = true)
    private String email;
}
