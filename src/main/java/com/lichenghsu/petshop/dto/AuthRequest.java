package com.lichenghsu.petshop.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "使用者登入與註冊請求格式")
public class AuthRequest {

    @Schema(description = "帳號名稱", example = "admin")
    private String username;

    @Schema(description = "登入密碼", example = "admin123")
    private String password;
}
